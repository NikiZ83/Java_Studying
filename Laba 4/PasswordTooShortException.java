public class PasswordTooShortException extends Exception {
    public PasswordTooShortException() {
        super("Пароль должен быть не менее 6 символов!");
    }
}