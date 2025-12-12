public class Main {
    public static void main(String[] args) {

        System.out.println("======= ЗАДАНИЕ 1 — Топ-10 слов в файле =======");
        TopWords.main(null);   // запускаем логику из задания 1


        System.out.println("\n======= ЗАДАНИЕ 2 — Демонстрация стека =======");
        Stack<Integer> stack = new Stack<>(10);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("pop():  " + stack.pop()); // 3
        System.out.println("peek(): " + stack.peek()); // 2

        stack.push(4);
        System.out.println("pop():  " + stack.pop()); // 4


        System.out.println("\n======= ЗАДАНИЕ 3 — Учёт продаж =======");

        // Используем ArrayList + HashMap
        SalesTracker tracker = new SalesTracker(
                java.util.ArrayList::new,
                java.util.HashMap::new
        );

        tracker.addSale("Apple", 1.0);
        tracker.addSale("Banana", 0.5);
        tracker.addSale("Apple", 1.0);
        tracker.addSale("Orange", 0.9);

        System.out.println("\nСписок продаж:");
        tracker.printSalesList();

        System.out.println("\nОбщая сумма продаж: " + tracker.getTotalSales());

        tracker.getMostPopular().ifPresent(
                e -> System.out.println("Самый популярный товар: " + e.getKey() +
                        " (продаж: " + e.getValue() + ")")
        );


        System.out.println("\n======= Конец демонстрации =======");
    }
}
