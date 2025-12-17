import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataManager {
    private List<Person> data = new ArrayList<>();

    private static class ProcessorEntry {
        final Object instance;
        final Method method;

        ProcessorEntry(Object instance, Method method) {
            this.instance = instance;
            this.method = method;
        }
    }

    private final List<ProcessorEntry> processors = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Регистрирует объект-обработчик: все методы с аннотацией @DataProcessor
     * должны иметь сигнатуру: List<Person> name(List<Person> input)
     */
    public void registerDataProcessor(Object processor) {
        for (Method m : processor.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(DataProcessor.class)) {
                m.setAccessible(true);
                processors.add(new ProcessorEntry(processor, m));
                System.out.println("Registered processor: " + processor.getClass().getSimpleName() + "." + m.getName());
            }
        }
    }

    public void setData(List<Person> data) {
        this.data = new ArrayList<>(data);
    }

    public List<Person> getData() {
        return Collections.unmodifiableList(data);
    }

    /**
     * Загружает данные из CSV-файла формата: name,age (без заголовка)
     */
    public void loadData(String sourcePath) throws IOException {
        List<Person> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sourcePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    int age = Integer.parseInt(parts[1].trim());
                    list.add(new Person(name, age));
                }
            }
        }
        this.data = list;
        System.out.println("Loaded " + list.size() + " records from " + sourcePath);
    }

    /**
     * Применяет зарегистрированные процессоры по очереди. Каждый процессор
     * получает текущий список и должен вернуть новый список. Внутри методов
     * рекомендуется использовать Stream API и parallelStream() для параллельной обработки.
     */
    public void processData() {
        for (ProcessorEntry entry : processors) {
            Callable<List<Person>> task = () -> {
                try {
                    Object result = entry.method.invoke(entry.instance, data);
                    if (result instanceof List) {
                        //noinspection unchecked
                        return (List<Person>) result;
                    } else {
                        System.err.println("Processor returned non-list: " + entry.method);
                        return data; // ничего не меняем
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error invoking processor " + entry.method, e);
                }
            };

            Future<List<Person>> future = executor.submit(task);
            try {
                List<Person> newData = future.get();
                if (newData != null) {
                    this.data = new ArrayList<>(newData);
                }
                System.out.println("Processor completed: " + entry.instance.getClass().getSimpleName() + "." + entry.method.getName() + " -> records=" + data.size());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Сохраняет данные в CSV-файл в формате name,age
     */
    public void saveData(String destinationPath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(destinationPath))) {
            for (Person p : data) {
                bw.write(p.toString());
                bw.newLine();
            }
        }
        System.out.println("Saved " + data.size() + " records to " + destinationPath);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
