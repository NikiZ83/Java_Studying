public class FileTooLargeException extends Exception {
    public FileTooLargeException() {
        super("Файл слишком большой (>10 МБ)!");
    }
}