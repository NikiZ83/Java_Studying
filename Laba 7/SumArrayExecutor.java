import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumArrayExecutor {

    static class SumTask implements Callable<Long> {
        private final int[] arr;
        private final int from, to; // [from, to)

        SumTask(int[] arr, int from, int to) {
            this.arr = arr; this.from = from; this.to = to;
        }

        @Override
        public Long call() {
            long s = 0;
            for (int i = from; i < to; i++) s += arr[i];
            return s;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int n = 10_000_000; // пример большого массива
        int threads = 4;    // можно изменить
        int[] arr = new int[n];
        Random r = new Random(42);
        for (int i = 0; i < n; i++) arr[i] = r.nextInt(100); // 0..99

        ExecutorService exec = Executors.newFixedThreadPool(threads);
        List<Future<Long>> futures = new ArrayList<>();

        int part = (n + threads - 1) / threads;
        for (int t = 0; t < threads; t++) {
            int from = t * part;
            int to = Math.min(n, from + part);
            if (from >= to) break;
            futures.add(exec.submit(new SumTask(arr, from, to)));
        }

        long total = 0;
        for (Future<Long> f : futures) total += f.get();

        exec.shutdown();
        System.out.println("Сумма элементов массива = " + total);

        // для проверки можно посчитать одним потоком:
        long check = 0;
        for (int v : arr) check += v;
        System.out.println("Проверка (однопоточная) = " + check);
    }
}
