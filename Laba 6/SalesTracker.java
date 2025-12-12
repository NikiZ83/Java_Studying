import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SalesTracker {
    // класс записи продажи
    public static class Sale {
        private final String product;
        private final double price;
        private final Date when;

        public Sale(String product, double price) {
            this.product = product;
            this.price = price;
            this.when = new Date();
        }

        public String getProduct() { return product; }
        public double getPrice() { return price; }
        public Date getWhen() { return when; }

        @Override
        public String toString() {
            return product + " — " + price;
        }
    }

    private final List<Sale> salesList;
    private final Map<String, Integer> counts; // для подсчета популярности
    private double total = 0.0;

    // конструктор с поставщиками реализаций коллекций
    public SalesTracker(Supplier<List<Sale>> listSupplier, Supplier<Map<String, Integer>> mapSupplier) {
        this.salesList = listSupplier.get();
        this.counts = mapSupplier.get();
    }

    // простой способ добавить продажу
    public void addSale(String product, double price) {
        Sale s = new Sale(product, price);
        salesList.add(s);
        counts.merge(product, 1, Integer::sum);
        total += price;
    }

    public List<Sale> getSalesList() {
        return Collections.unmodifiableList(salesList);
    }

    public Map<String, Integer> getCounts() {
        return Collections.unmodifiableMap(counts);
    }

    public double getTotalSales() {
        return total;
    }

    // Наиболее популярный товар (по количеству продаж)
    public Optional<Map.Entry<String, Integer>> getMostPopular() {
        return counts.entrySet().stream().max(Map.Entry.comparingByValue());
    }

    // вывод списка проданных товаров
    public void printSalesList() {
        if (salesList.isEmpty()) {
            System.out.println("Продаж нет");
            return;
        }
        System.out.println("Список продаж:");
        for (Sale s : salesList) {
            System.out.printf("- %s : %.2f%n", s.getProduct(), s.getPrice());
        }
    }

    // пример демонстрации разных вариантов
    public static void main(String[] args) {
        // 1) ArrayList + HashMap
        SalesTracker t1 = new SalesTracker(ArrayList::new, HashMap::new);
        t1.addSale("apple", 1.0);
        t1.addSale("banana", 0.5);
        t1.addSale("apple", 1.0);
        System.out.println("=== ArrayList + HashMap ===");
        t1.printSalesList();
        System.out.println("Total: " + t1.getTotalSales());
        t1.getMostPopular().ifPresent(e -> System.out.println("Most popular: " + e.getKey() + " — " + e.getValue()));

        // 2) LinkedList + TreeMap
        SalesTracker t2 = new SalesTracker(LinkedList::new, TreeMap::new);
        t2.addSale("orange", 0.8);
        t2.addSale("kiwi", 1.2);
        t2.addSale("orange", 0.8);
        System.out.println("\n=== LinkedList + TreeMap ===");
        t2.printSalesList();
        System.out.println("Total: " + t2.getTotalSales());
        t2.getMostPopular().ifPresent(e -> System.out.println("Most popular: " + e.getKey() + " — " + e.getValue()));

        // 3) ConcurrentHashMap + AtomicInteger (пример многопоточной части)
        System.out.println("\n=== ConcurrentHashMap + AtomicInteger (пример) ===");
        ConcurrentCounterSales cc = new ConcurrentCounterSales();
        cc.addSale("milk", 1.1);
        cc.addSale("milk", 1.1);
        cc.addSale("bread", 0.9);
        cc.printCounts();
        System.out.println("Total: " + cc.getTotalSales());
    }

    // вспомогательный класс для варианта с ConcurrentHashMap + AtomicInteger
    public static class ConcurrentCounterSales {
        private final List<Sale> sales = Collections.synchronizedList(new ArrayList<>());
        private final ConcurrentHashMap<String, AtomicInteger> counts = new ConcurrentHashMap<>();
        private volatile double total = 0.0;

        public void addSale(String product, double price) {
            sales.add(new Sale(product, price));
            counts.computeIfAbsent(product, k -> new AtomicInteger(0)).incrementAndGet();
            synchronized (this) {
                total += price;
            }
        }

        public void printCounts() {
            System.out.println("Counts (concurrent):");
            counts.forEach((k, v) -> System.out.println(k + " -> " + v.get()));
        }

        public double getTotalSales() { return total; }
    }
}
