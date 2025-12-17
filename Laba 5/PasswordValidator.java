import java.util.regex.*;
import java.util.Scanner;

public class PasswordValidator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите пароль: ");
        String password = sc.nextLine();

        String regex = "^(?=.{8,16}$)(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$";

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches()) {
                System.out.println("Пароль корректен.");
            } else {
                System.out.println("Неверный пароль. Требования:");
                System.out.println("- Только латинские буквы и цифры");
                System.out.println("- Длина 8–16 символов");
                System.out.println("- Как минимум одна заглавная буква");
                System.out.println("- Как минимум одна цифра");
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в регулярном выражении: " + e.getDescription());
        }
    }
}
