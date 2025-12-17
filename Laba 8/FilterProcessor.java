import java.util.List;
import java.util.stream.Collectors;

public class FilterProcessor {

    // Оставляем только людей 18 лет и старше
    @DataProcessor
    public List<Person> filterAdults(List<Person> input) {
        return input.parallelStream()
                .filter(p -> p.getAge() >= 18)
                .collect(Collectors.toList());
    }
}
TransformProcessor.java
import java.util.List;
import java.util.stream.Collectors;

public class TransformProcessor {

    // Приводим имена к верхнему регистру (параллельно)
    @DataProcessor
    public List<Person> uppercaseNames(List<Person> input) {
        return input.parallelStream()
                .map(p -> new Person(p.getName().toUpperCase(), p.getAge()))
                .collect(Collectors.toList());
    }
}
