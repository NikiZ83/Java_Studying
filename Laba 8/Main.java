import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataManager dm = new DataManager();

        // Регистрируем обработчики
        dm.registerDataProcessor(new FilterProcessor());
        dm.registerDataProcessor(new TransformProcessor());
        dm.registerDataProcessor(new AggregateProcessor());

        // Попытка загрузить input.csv, иначе используем встроенные данные
        File in = new File("input.csv");
        if (in.exists()) {
            try {
                dm.loadData(in.getPath());
            } catch (IOException e) {
                System.err.println("Error loading input.csv: " + e.getMessage());
                dm.setData(sampleData());
            }
        } else {
            System.out.println("input.csv not found — using sample data");
            dm.setData(sampleData());
        }

        // Обработка
        dm.processData();

        // Сохранение результатов
        try {
            dm.saveData("output.csv");
        } catch (IOException e) {
            System.err.println("Error saving output.csv: " + e.getMessage());
        }

        dm.shutdown();
    }

    private static List<Person> sampleData() {
        return Arrays.asList(
                new Person("Alice", 23),
                new Person("Bob", 17),
                new Person("Charlie", 35),
                new Person("Diana", 29),
                new Person("Eve", 15)
        );
    }
}
