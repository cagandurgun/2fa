import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class TwoFactorCodeGenerator {

    private static final String CODE_FILE_PATH = "two_factor_codes.txt";
    private static final int CODE_LENGTH = 6;

    public static String generateAndSaveCode() {
        String generatedCode = generateCode();
        saveCodeToFile(generatedCode);
        return generatedCode;
    }

    private static String generateCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private static void saveCodeToFile(String code) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CODE_FILE_PATH))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyCode(String enteredCode) {
        String storedCode = readStoredCode();
        return enteredCode.equals(storedCode);
    }

    private static String readStoredCode() {
        try {
            // Dosyadan kayıtlı kodu oku
            return Files.readString(Paths.get(CODE_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
