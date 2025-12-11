import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ExceptionLogger {
    private static final String LOG_FILE = "exception_log.txt";

    public static void logException(Exception e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.println("[" + LocalDateTime.now() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace(pw);
            pw.println("----------------------------------------");
        } catch (IOException ex) {
            System.out.println("Не удалось записать лог: " + ex.getMessage());
        }
    }
}