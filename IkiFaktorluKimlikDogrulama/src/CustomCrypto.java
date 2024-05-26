import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CustomCrypto {

    // Metni SHA-256 ile şifreleme
    public static String hashPassword(String plainTextPassword) {
        try {
            // Salt oluştur
            byte[] salt = generateSalt();

            // Salt ile şifreleme
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt);
            byte[] hashedBytes = digest.digest(plainTextPassword.getBytes("UTF-8"));

            // Salt ve şifrelenmiş veriyi birleştir
            byte[] combined = new byte[hashedBytes.length + salt.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedBytes, 0, combined, salt.length, hashedBytes.length);

            // Base64 ile kodla
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Şifreyi doğrulama
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        try {
            // Base64 ile çöz
            byte[] combined = Base64.getDecoder().decode(hashedPassword);

            // Salt ve şifrelenmiş veriyi ayır
            int saltLength = 16;
            byte[] salt = new byte[saltLength];
            byte[] hashedBytes = new byte[combined.length - saltLength];
            System.arraycopy(combined, 0, salt, 0, saltLength);
            System.arraycopy(combined, saltLength, hashedBytes, 0, hashedBytes.length);

            // Şifreleme işlemi
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt);
            byte[] testHashedBytes = digest.digest(plainTextPassword.getBytes("UTF-8"));

            // Şifrelerin eşleşip eşleşmediğini kontrol et
            for (int i = 0; i < hashedBytes.length; i++) {
                if (hashedBytes[i] != testHashedBytes[i]) {
                    return false;
                }
            }
            return true;
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Rastgele salt oluşturma
    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
