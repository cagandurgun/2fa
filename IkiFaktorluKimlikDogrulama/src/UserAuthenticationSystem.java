import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAuthenticationSystem {

    private static final String USER_FILE_PATH = "users.txt";
    private static final String PASSWORD_FILE_PATH = "passwords.txt";
    private static final String TWO_FACTOR_CODE_FILE_PATH = "two_factor_codes.txt";
    private static final int MIN_PASSWORD_LENGTH = 8;

    private Map<String, String> users;

    public UserAuthenticationSystem() {
        this.users = loadUsersFromFile();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hoş Geldiniz!");

        while (true) {
            System.out.println("1 - Giriş Yap");
            System.out.println("2 - Kullanıcı Oluştur");
            System.out.println("3 - Çıkış");
            System.out.print("Seçiminiz: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                login();
            } else if (choice == 2) {
                createUser();
            } else if (choice == 3) {
                System.out.println("Çıkış yapılıyor...");
                break;
            } else {
                System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
            }
        }
    }

    private void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Kullanıcı Adı: ");
        String username = scanner.nextLine().trim();

        if (!users.containsKey(username)) {
            System.out.println("Kullanıcı bulunamadı. Lütfen tekrar deneyin.");
            return;
        }

        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        if (!checkPassword(username, password)) {
            System.out.println("Giriş başarısız. Kullanıcı adı veya şifre hatalı.");
        } else {
            // İki aşamalı kimlik doğrulaması
            String generatedCode = TwoFactorCodeGenerator.generateAndSaveCode();
            System.out.println("İki Aşamalı Kod dosyaya yazıldı. Lütfen " + TWO_FACTOR_CODE_FILE_PATH + " dosyasını kontrol edin.");

            System.out.print("İki Aşamalı Kod: ");
            String enteredCode = scanner.nextLine().trim();

            if (TwoFactorCodeGenerator.verifyCode(enteredCode)) {
                System.out.println("Giriş başarılı! İki aşamalı kimlik doğrulaması başarılı.");
            } else {
                System.out.println("Giriş başarısız. İki aşamalı kimlik doğrulaması başarısız.");
            }
        }
    }

    private void createUser() {
        Scanner scanner = new Scanner(System.in);

        String newUsername;
        do {
            System.out.print("Yeni Kullanıcı Adı: ");
            newUsername = scanner.nextLine().trim();
            if (users.containsKey(newUsername)) {
                System.out.println("Bu kullanıcı adı zaten kullanılmaktadır. Lütfen farklı bir kullanıcı adı seçin.");
            }
        } while (users.containsKey(newUsername));

        String newPassword;
        do {
            System.out.print("Yeni Şifre: ");
            newPassword = scanner.nextLine();
            if (!validatePasswordStrength(newPassword)) {
                System.out.println("Şifre zayıf. En az " + MIN_PASSWORD_LENGTH + " karakter uzunluğunda olmalıdır.");
            }
        } while (!validatePasswordStrength(newPassword));

        users.put(newUsername, CustomCrypto.hashPassword(newPassword));
        saveUsersToFile();
        System.out.println("Kullanıcı başarıyla oluşturuldu!");
    }

    private boolean validatePasswordStrength(String password) {
        // Şifre uzunluğu kontrolü
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        // Türkçe karakter kontrolü
        Pattern turkishPattern = Pattern.compile("[çÇğĞıİöÖşŞüÜ]");
        Matcher matcher = turkishPattern.matcher(password);
        return !matcher.find();
    }

    private boolean checkPassword(String username, String enteredPassword) {
        String storedPassword = users.get(username);
        return CustomCrypto.checkPassword(enteredPassword, storedPassword);
    }

    private Map<String, String> loadUsersFromFile() {
        Map<String, String> users = new HashMap<>();

        try (BufferedReader userReader = new BufferedReader(new FileReader(USER_FILE_PATH));
             BufferedReader passwordReader = new BufferedReader(new FileReader(PASSWORD_FILE_PATH))) {

            String username;
            while ((username = userReader.readLine()) != null) {
                String password = passwordReader.readLine();
                users.put(username, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    private void saveUsersToFile() {
        try (BufferedWriter userWriter = new BufferedWriter(new FileWriter(USER_FILE_PATH));
             BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(PASSWORD_FILE_PATH))) {

            for (Map.Entry<String, String> entry : users.entrySet()) {
                userWriter.write(entry.getKey() + "\n");
                passwordWriter.write(entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
