// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Выберите задание: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число!");
                continue;
            }

            if (choice == 0) {
                System.out.println("Программа завершена.");
                break;
            }

            try {
                switch (choice) {
                    case 1:
                        runArrayAverage(scanner);
                        break;
                    case 2:
                        runFileCopy(scanner);
                        break;
                    case 3:
                        testCustomExceptions();
                        break;
                    default:
                        System.out.println("Неверный выбор! Попробуйте снова.");
                }
            } catch (Exception e) {
                ExceptionLogger.logException(e);
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // Выводит главное меню программы
    private static void printMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println(" ЛАБОРАТОРНАЯ РАБОТА: ИСКЛЮЧЕНИЯ");
        System.out.println("=".repeat(40));
        System.out.println("1. Среднее арифметическое массива");
        System.out.println("2. Копирование файла (все ошибки)");
        System.out.println("3. Тестирование 8 пользовательских исключений");
        System.out.println("0. Выход");
        System.out.println("=".repeat(40));
    }

    // Запускает задание 1 – вычисление среднего арифметического
    private static void runArrayAverage(Scanner scanner) {
        ArrayAverage.main(new String[]{});
    }

    // Запускает задание 2 – копирование файла с обработкой всех ошибок
    private static void runFileCopy(Scanner scanner) {
        System.out.print("Введите путь к исходному файлу: ");
        String source = scanner.nextLine().trim();
        System.out.print("Введите путь к файлу назначения: ");
        String destination = scanner.nextLine().trim();

        if (source.isEmpty() || destination.isEmpty()) {
            System.out.println("Ошибка: пути не могут быть пустыми!");
            return;
        }

        FileCopier.copyFile(source, destination);
    }

    // Запускает задание 3 – тестирование 8 пользовательских исключений
    private static void testCustomExceptions() {
        System.out.println("\nТестирование пользовательских исключений:");
        testException(() -> divide(10, 0), "Деление на ноль");
        testException(() -> setAge(-5), "Некорректный возраст");
        testException(() -> setName(""), "Пустое имя");
        testException(() -> checkFileSize(15_000_000), "Слишком большой файл");
        testException(() -> validateEmail("invalid"), "Некорректный email");
        testException(() -> withdraw(100, 150), "Отрицательный баланс");
        testException(() -> setPassword("123"), "Короткий пароль");
        testException(() -> findUser("unknown"), "Пользователь не найден");
    }

    // Функциональный интерфейс для методов, которые могут бросить исключение
    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }

    // Универсальная обёртка для тестирования исключений
    private static void testException(ThrowingRunnable action, String description) {
        try {
            action.run();
        } catch (Exception e) {
            System.out.println("Исключение: " + description);
            ExceptionLogger.logException(e);
        }
    }

    // Методы, бросающие пользовательские исключения (для тестов)

    private static void divide(int a, int b) throws DivisionByZeroException {
        if (b == 0) throw new DivisionByZeroException();
        System.out.println(a / b);
    }

    private static void setAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) throw new InvalidAgeException();
    }

    private static void setName(String name) throws EmptyNameException {
        if (name == null || name.trim().isEmpty()) throw new EmptyNameException();
    }

    private static void checkFileSize(long size) throws FileTooLargeException {
        if (size > 10_000_000) throw new FileTooLargeException();
    }

    private static void validateEmail(String email) throws InvalidEmailException {
        if (!email.contains("@") || !email.contains(".")) throw new InvalidEmailException();
    }

    private static void withdraw(double balance, double amount) throws NegativeBalanceException {
        if (balance - amount < 0) throw new NegativeBalanceException();
    }

    private static void setPassword(String pass) throws PasswordTooShortException {
        if (pass.length() < 6) throw new PasswordTooShortException();
    }

    private static void findUser(String username) throws UserNotFoundException {
        if (!username.equals("admin")) throw new UserNotFoundException();
    }
}