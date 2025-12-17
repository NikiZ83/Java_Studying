import java.util.regex.*;
import java.util.Scanner;

public class NumberFinder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите текст:");
        String text = sc.nextLine();

        // Регекс: целые и десятичные числа с опциональным знаком
        String regex = "\\b[-+]?\\d+(?:\\.\\d+)?\\b";

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            boolean found = false;
            while (matcher.find()) {
                System.out.println("Найдено число: " + matcher.group());
                found = true;
            }
            if (!found) System.out.println("Числа не найдены.");
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в паттерне: " + e.getDescription());
        }
    }
}
