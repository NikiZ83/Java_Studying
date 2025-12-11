public class InvalidEmailException extends Exception {
    public InvalidEmailException() {
        super("Некорректный email!");
    }
}