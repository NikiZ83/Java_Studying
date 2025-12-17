import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopWords {
    public static void main(String[] args) {
        // путь к файлу (поменяйте на свой)
        String filePath = "C:\\Users\\Oliver\\Desktop\\ВУЗ\\ИТиП\\Java\\Laba 6\\text.txt";
        File file = new File(filePath);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + filePath);
            return;
        }

        // Map для подсчета слов
        Map<String, Integer> counts = new HashMap<>();

        // читаем файл по словам
        while (scanner.hasNext()) {
            String token = scanner.next();
            // нормализация: оставить буквы и цифры, привести к lower case
            String word = token.toLowerCase().replaceAll("[^\\p{L}\\p{Nd}]+", "");
            if (word.isEmpty()) continue;
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        // закрываем Scanner
        scanner.close();

        // создаем список из элементов Map
        List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());

        // сортируем список по убыванию количества повторений
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int cmp = o2.getValue().compareTo(o1.getValue()); // сначала по убыванию частоты
                if (cmp != 0) return cmp;
                return o1.getKey().compareTo(o2.getKey()); // для стабильности: по слову
            }
        });

        // выводим топ-10 слов (или меньше, если слов < 10)
        System.out.println("Top words:");
        int limit = Math.min(10, list.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> e = list.get(i);
            System.out.printf("%2d. %s — %d%n", i + 1, e.getKey(), e.getValue());
        }
    }
}
