public class EmptyNameException extends Exception {
    public EmptyNameException() {
        super("Имя не может быть пустым!");
    }
}