import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WarehouseSimulation {

    static class Warehouse {
        private final Queue<Integer> items = new LinkedList<>();
        private int currentLoad = 0;
        private final int CAPACITY;

        public Warehouse(int capacity) {
            this.CAPACITY = capacity;
        }

        public synchronized void addItem(int weight) {
            items.add(weight);
        }

        // Возвращает вес следующего товара или -1 если нет
        public synchronized int pollNext() {
            Integer w = items.poll();
            return w == null ? -1 : w;
        }

        // Попытка положить товар в текущую партию.
        // Возвращает:
        //  0 — успешно добавлен и общего веса < CAPACITY,
        //  1 — успешно добавлен и достигли/превысили CAPACITY (необходимо отправить),
        // -1 — товар не помещается (например вес > CAPACITY), в этом простом симуляторе
        //      мы рассматриваем, что такого не происходит, но обрабатываем на всякий случай.
        public synchronized int tryAddToLoad(int weight) {
            if (weight > CAPACITY) return -1;
            if (currentLoad + weight < CAPACITY) {
                currentLoad += weight;
                System.out.printf("[%s] взял %d кг, текущая партия = %d кг%n",
                        Thread.currentThread().getName(), weight, currentLoad);
                return 0;
            } else {
                // добавляем и отправляем (если ровно равно или превышает)
                currentLoad += weight;
                System.out.printf("[%s] взял %d кг, текущая партия = %d кг (порог достигнут)%n",
                        Thread.currentThread().getName(), weight, currentLoad);
                return 1;
            }
        }

        // Отправка — очищаем текущую партию и имитируем разгрузку
        public synchronized void sendLoad(String byWho) {
            if (currentLoad == 0) return;
            System.out.printf("### Партия собрана (%d кг) — отправляем (инициатор: %s) ###%n", currentLoad, byWho);
            try {
                // имитация доставки/разгрузки
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.printf("+++ Разгружено %d кг +++%n", currentLoad);
            currentLoad = 0;
        }

        public synchronized boolean isEmpty() {
            return items.isEmpty();
        }

        public synchronized int remainingItems() {
            return items.size();
        }
    }

    static class Loader implements Runnable {
        private final Warehouse warehouse;

        Loader(Warehouse w) { this.warehouse = w; }

        @Override
        public void run() {
            while (true) {
                int next;
                // берем следующий товар
                synchronized (warehouse) {
                    next = warehouse.pollNext();
                }
                if (next == -1) {
                    // если нет товаров — если кто-то ещё собирал партию, пусть отправят
                    synchronized (warehouse) {
                        if (warehouse.currentLoad > 0) {
                            warehouse.sendLoad(Thread.currentThread().getName());
                        }
                    }
                    break;
                }

                int res;
                synchronized (warehouse) {
                    res = warehouse.tryAddToLoad(next);
                }
                if (res == -1) {
                    // невозможный вес — просто пропускаем в этой реализации
                    System.out.printf("[%s] товар весит %d кг — пропуск%n", Thread.currentThread().getName(), next);
                } else if (res == 1) {
                    // порог достигнут — этот поток инициирует отправку
                    synchronized (warehouse) {
                        warehouse.sendLoad(Thread.currentThread().getName());
                    }
                }

                // симуляция времени на перенос одного товара
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
            }
            System.out.printf("[%s] завершил работу.%n", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Warehouse wh = new Warehouse(150);

        // Создадим случайный набор товаров (вес 5..50)
        Random r = new Random(12345);
        for (int i = 0; i < 40; i++) {
            wh.addItem(5 + r.nextInt(46));
        }
        System.out.println("Всего товаров: " + wh.remainingItems());

        Thread loader1 = new Thread(new Loader(wh), "Грузчик-1");
        Thread loader2 = new Thread(new Loader(wh), "Грузчик-2");
        Thread loader3 = new Thread(new Loader(wh), "Грузчик-3");

        loader1.start();
        loader2.start();
        loader3.start();

        loader1.join();
        loader2.join();
        loader3.join();

        System.out.println("Все товары перенесены.");
    }
}
