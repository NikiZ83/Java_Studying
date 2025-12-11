import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayAverage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер массива: ");
        
        try {
            int n = scanner.nextInt();
            if (n <= 0) throw new IllegalArgumentException("Размер должен быть положительным");

            int[] arr = new int[n];
            System.out.println("Введите " + n + " чисел:");

            int sum = 0;
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
                sum += arr[i];
            }

            double average = (double) sum / n;
            System.out.println("Среднее арифметическое: " + average);

        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введено не число!");
        } catch (NegativeArraySizeException | IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка: выход за границы массива!");
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка: " + e.getMessage());
        }
    }
}