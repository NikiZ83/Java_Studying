import java.util.HashMap;
import java.util.Map;

/**
 * Демонстрация работы:
 * - собственная HashTable (HashTable.java)
 * - встроенный HashMap для хранения студентов (Student.java)
 *
 * Файл: LabHashTables.java
 */
public class LabHashTables {

    public static void main(String[] args) {
        // --- Демонстрация собственной HashTable ---
        System.out.println("=== Demo: собственная HashTable ===");
        HashTable<String, Integer> ht = new HashTable<>();
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("three", 3);
        System.out.println("size = " + ht.size());            // 3
        System.out.println("get(\"two\") = " + ht.get("two"));// 2
        ht.put("two", 22);
        System.out.println("get(\"two\") after update = " + ht.get("two")); // 22
        System.out.println("remove(\"one\") = " + ht.remove("one")); // 1
        System.out.println("isEmpty = " + ht.isEmpty());      // false
        System.out.println("size after remove = " + ht.size()); // 2

        // --- Демонстрация HashMap для студентов (вариант 1) ---
        System.out.println("\n=== Demo: HashMap<String, Student> ===");
        Map<String, Student> students = new HashMap<>();
        insertStudent(students, "RB001", new Student("Ivan", "Ivanov", 20, 4.5));
        insertStudent(students, "RB002", new Student("Olga", "Petrova", 21, 4.8));
        insertStudent(students, "RB003", new Student("Petr", "Sidorov", 19, 3.9));

        System.out.println("Find RB002: " + findStudent(students, "RB002"));
        removeStudent(students, "RB003");
        System.out.println("After removing RB003, contains RB003? " + (findStudent(students, "RB003") != null));
        System.out.println("All students:");
        for (Map.Entry<String, Student> e : students.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }

    // Вставка студента (ключ — номер зачетной книжки)
    public static void insertStudent(Map<String, Student> map, String recordBookNumber, Student student) {
        map.put(recordBookNumber, student);
    }

    // Поиск студента по номеру зачетки
    public static Student findStudent(Map<String, Student> map, String recordBookNumber) {
        return map.get(recordBookNumber);
    }

    // Удаление студента по номеру зачетки
    public static Student removeStudent(Map<String, Student> map, String recordBookNumber) {
        return map.remove(recordBookNumber);
    }
}
