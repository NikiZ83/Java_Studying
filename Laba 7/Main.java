public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("======================================");
        System.out.println("Лабораторная работа: Многопоточность");
        System.out.println("======================================\n");

        /* =======================
           ЗАДАНИЕ 1
           ======================= */
        System.out.println("ЗАДАНИЕ 1: Сумма элементов массива (ExecutorService)");
        SumArrayExecutor.main(args);
        System.out.println();

        /* =======================
           ЗАДАНИЕ 2
           ======================= */
        System.out.println("ЗАДАНИЕ 2: Поиск максимума в матрице (Thread на строку)");
        MaxInMatrixThreads.main(args);
        System.out.println();

        /* =======================
           ЗАДАНИЕ 3
           ======================= */
        System.out.println("ЗАДАНИЕ 3: Симуляция склада (3 грузчика, synchronized)");
        WarehouseSimulation.main(args);
        System.out.println();

        System.out.println("======================================");
        System.out.println("Работа завершена");
        System.out.println("======================================");
    }
}
