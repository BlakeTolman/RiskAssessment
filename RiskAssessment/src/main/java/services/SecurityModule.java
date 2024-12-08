package services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

//Encrypts and decrypts data
public class SecurityModule {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY_STRING = "d4b2c897e9f34b7c8d3a5e6fb072c819";
    private final SecretKeySpec secretKey;

    public SecurityModule() {
        secretKey = new SecretKeySpec(SECRET_KEY_STRING.getBytes(), ALGORITHM);
    }

    // Encrypt data using AES
    public String encryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    // Decrypt data using AES
    public String decryptData(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decodedBytes));
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}
