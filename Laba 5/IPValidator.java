import java.util.regex.*;
import java.util.Scanner;

public class IPValidator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите IP-адрес: ");
        String ip = sc.nextLine().trim();

        String num = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
        String regex = "^(" + num + "\\.){3}" + num + "$";

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(ip);
            if (matcher.matches()) {
                System.out.println("IP-адрес корректен.");
            } else {
                System.out.println("Некорректный IP-адрес.");
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в паттерне: " + e.getDescription());
        }
    }
}
