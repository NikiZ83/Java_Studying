import java.util.List;

public class AggregateProcessor {

    // Вычисляем средний возраст и печатаем в консоль; возвращаем исходный список
    @DataProcessor
    public List<Person> aggregateStats(List<Person> input) {
        double avg = input.parallelStream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0.0);
        System.out.println("[Aggregate] average age = " + avg);
        return input; // агрегирующая операция не меняет набор записей
    }
}
