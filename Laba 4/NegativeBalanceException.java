public class NegativeBalanceException extends Exception {
    public NegativeBalanceException() {
        super("Баланс не может быть отрицательным!");
    }
}