import java.util.regex.*;
import java.util.Scanner;

public class MarkLowerUpperPairs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите текст:");
        String text = sc.nextLine();

        // Unicode-aware: \p{Ll}=lowercase letter, \p{Lu}=uppercase letter
        String regex = "([\\p{Ll}])([\\p{Lu}])";

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            // Заменяем каждую пару на !pair!
            String result = matcher.replaceAll("!$1$2!");
            System.out.println("Результат: " + result);
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в паттерне: " + e.getDescription());
        }
    }
}
