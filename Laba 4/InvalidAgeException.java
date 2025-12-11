public class InvalidAgeException extends Exception {
    public InvalidAgeException() {
        super("Возраст должен быть от 0 до 150!");
    }
}