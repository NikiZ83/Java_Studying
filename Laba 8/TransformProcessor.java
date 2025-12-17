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