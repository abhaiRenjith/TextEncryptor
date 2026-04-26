package service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * AesCipher implements the CipherService interface using AES (Advanced Encryption Standard).
 *
 * This class provides methods to:
 * - Encrypt plain text using a user-provided key
 * - Decrypt encrypted text back to its original form
 *
 * Notes:
 * - Uses AES symmetric encryption (same key for encrypt/decrypt)
 * - Key is derived using SHA-256 hashing
 * - Only first 16 bytes are used (AES-128)
 * - Output is encoded using Base64 for readability
 */
public class AesCipher implements CipherService {

    /**
     * Encrypts the given plain text using AES encryption.
     *
     * @param text The plain text to encrypt
     * @param key  The user-provided key (password)
     * @return Encrypted text encoded in Base64 format
     */
    @Override
    public String encrypt(String text, String key) {
        try {
            // Generate AES key from user input
            SecretKeySpec secretKey = generateKey(key);

            // Create AES cipher instance
            Cipher cipher = Cipher.getInstance("AES");

            // Initialize cipher in ENCRYPT mode
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Perform encryption
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());

            // Convert encrypted bytes to Base64 string for display
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            // Wrap exception into runtime error
            throw new RuntimeException("AES encryption failed: " + e.getMessage());
        }
    }

    /**
     * Decrypts the given encrypted text using AES decryption.
     *
     * @param text The encrypted text (Base64 encoded)
     * @param key  The same key used during encryption
     * @return Original decrypted plain text
     */
    @Override
    public String decrypt(String text, String key) {
        try {
            // Generate AES key from user input
            SecretKeySpec secretKey = generateKey(key);

            // Create AES cipher instance
            Cipher cipher = Cipher.getInstance("AES");

            // Initialize cipher in DECRYPT mode
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decode Base64 string back to bytes
            byte[] decodedBytes = Base64.getDecoder().decode(text);

            // Perform decryption
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            // Convert bytes back to string
            return new String(decryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed: " + e.getMessage());
        }
    }

    /**
     * Generates a SecretKeySpec for AES using SHA-256 hashing.
     *
     * Process:
     * 1. Hash the input key using SHA-256
     * 2. Take first 16 bytes (128 bits) for AES-128
     * 3. Create SecretKeySpec object
     *
     * @param key The user-provided key
     * @return AES SecretKeySpec
     * @throws Exception if hashing fails
     */
    private SecretKeySpec generateKey(String key) throws Exception {

        // Create SHA-256 digest
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Hash the input key
        byte[] keyBytes = digest.digest(key.getBytes());

        // Create a 16-byte array (AES-128 key size)
        byte[] finalKey = new byte[16];

        // Copy first 16 bytes from hashed key
        System.arraycopy(keyBytes, 0, finalKey, 0, 16);

        // Return AES key specification
        return new SecretKeySpec(finalKey, "AES");
    }
}