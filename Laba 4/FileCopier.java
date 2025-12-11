import java.io.*;

public class FileCopier {

    public static void copyFile(String sourcePath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            System.out.println("Открываем исходный файл: " + sourcePath);
            fis = new FileInputStream(sourcePath);  

            System.out.println("Открываем файл для записи: " + destPath);
            fos = new FileOutputStream(destPath);   

            byte[] buffer = new byte[1024];
            int bytesRead;

            System.out.println("Копируем данные...");
            while ((bytesRead = fis.read(buffer)) != -1) {  
                fos.write(buffer, 0, bytesRead);      
            }

            System.out.println("Файл успешно скопирован!");

        } catch (FileNotFoundException e) {
            if (!new File(sourcePath).exists()) {
                System.out.println("ОШИБКА ОТКРЫТИЯ: Исходный файл не существует: " + sourcePath);
            } else if (!new File(destPath).getParentFile().exists()) {
                System.out.println("ОШИБКА ОТКРЫТИЯ: Папка для записи не существует: " + new File(destPath).getParent());
            } else {
                System.out.println("ОШИБКА ОТКРЫТИЯ: Нет доступа к файлу: " + e.getMessage());
            }

        } catch (SecurityException e) {
            System.out.println("ОШИБКА ДОСТУПА: Нет прав на чтение/запись: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("ОШИБКА ЧТЕНИЯ/ЗАПИСИ: " + e.getMessage());

        } finally {
            try {
                if (fis != null) {
                    System.out.println("Закрываем исходный файл...");
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println("ОШИБКА ПРИ ЗАКРЫТИИ ИСХОДНОГО ФАЙЛА: " + e.getMessage());
            }

            try {
                if (fos != null) {
                    System.out.println("Закрываем файл назначения...");
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println("ОШИБКА ПРИ ЗАКРЫТИИ ФАЙЛА НАЗНАЧЕНИЯ: " + e.getMessage());
            }
        }
    }
}