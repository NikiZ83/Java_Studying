import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MaxInMatrixThreads {

    static class RowMaxRunnable implements Runnable {
        private final int[] row;
        private final AtomicInteger globalMax;

        RowMaxRunnable(int[] row, AtomicInteger globalMax) {
            this.row = row; this.globalMax = globalMax;
        }

        @Override
        public void run() {
            int localMax = Integer.MIN_VALUE;
            for (int v : row) if (v > localMax) localMax = v;
            // атомарно обновляем глобальный максимум
            globalMax.updateAndGet(prev -> Math.max(prev, localMax));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int rows = 8;
        int cols = 1_000_000; // для демонстрации нагрузки можно уменьшить
        int[][] matrix = new int[rows][cols];
        Random r = new Random(123);

        // заполним матрицу случайными числами
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                matrix[i][j] = r.nextInt(1_000_000);

        AtomicInteger globalMax = new AtomicInteger(Integer.MIN_VALUE);
        Thread[] threads = new Thread[rows];

        for (int i = 0; i < rows; i++) {
            threads[i] = new Thread(new RowMaxRunnable(matrix[i], globalMax), "Row-" + i);
            threads[i].start();
        }

        for (Thread t : threads) t.join();

        System.out.println("Максимальный элемент матрицы = " + globalMax.get());

        // проверка однопоточная (опционально)
        int check = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (matrix[i][j] > check) check = matrix[i][j];
        System.out.println("Проверка (однопоточная) = " + check);
    }
}
