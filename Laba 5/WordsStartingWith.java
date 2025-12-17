	import java.util.regex.*;
import java.util.Scanner;

public class WordsStartingWith {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите текст:");
        String text = sc.nextLine();
        System.out.print("Введите начальную букву: ");
        String letter = sc.nextLine();

        if (letter == null || letter.isEmpty()) {
            System.out.println("Буква не введена.");
            return;
        }
        // Берём только первый символ введённого (возможно буква)
        String first = letter.substring(0, 1);

        // Шаблон: слово, начинающееся с заданной буквы, затем буквы (Unicode)
        String regex = "\\b" + Pattern.quote(first) + "[\\p{L}]*\\b";

        try {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(text);
            boolean found = false;
            while (matcher.find()) {
                System.out.println("Найдено слово: " + matcher.group());
                found = true;
            }
            if (!found) System.out.println("Слов, начинающихся с '" + first + "', не найдено.");
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в паттерне: " + e.getDescription());
        }
    }
}
