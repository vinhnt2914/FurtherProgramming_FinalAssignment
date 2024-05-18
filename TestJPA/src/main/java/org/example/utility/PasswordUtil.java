package org.example.utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordUtil {
    private static final Logger logger = Logger.getLogger(PasswordUtil.class.getName());
    private static final String ALGORITHM = "AES";
    private static final byte[] key = "MySuperSecretKey".getBytes(); // Replace with your secret key

    public static String encrypt(String password) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Encryption error", e);
            throw new RuntimeException(e);
        }
    }
}
