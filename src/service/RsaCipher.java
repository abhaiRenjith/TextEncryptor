package service;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

/**
 * RsaCipher implements the CipherService interface using RSA (asymmetric encryption).
 *
 * RSA uses two keys:
 * - Public Key → used for encryption
 * - Private Key → used for decryption
 *
 * Notes:
 * - A key pair is generated once when the object is created
 * - The same instance must be used for both encryption and decryption
 * - Output is encoded in Base64 for readability
 * - Suitable for small data only (not large text)
 */
public class RsaCipher implements CipherService {

    // Stores the RSA public/private key pair
    private KeyPair keyPair;

    /**
     * Constructor: Generates a new RSA key pair.
     * Key size: 2048 bits (secure standard)
     */
    public RsaCipher() {
        try {
            // Create key pair generator for RSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            // Set key size (2048 bits is standard secure size)
            keyPairGenerator.initialize(2048);

            // Generate public + private key pair
            keyPair = keyPairGenerator.generateKeyPair();

        } catch (Exception e) {
            throw new RuntimeException("RSA key generation failed: " + e.getMessage());
        }
    }

    /**
     * Encrypts plain text using the RSA public key.
     *
     * @param text The plain text to encrypt
     * @param key  Not used (kept for interface consistency)
     * @return Encrypted text encoded in Base64
     */
    @Override
    public String encrypt(String text, String key) {
        try {
            // Create RSA cipher instance
            Cipher cipher = Cipher.getInstance("RSA");

            // Initialize cipher with PUBLIC key for encryption
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

            // Perform encryption
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());

            // Convert encrypted bytes to Base64 string
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("RSA encryption failed: " + e.getMessage());
        }
    }

    /**
     * Decrypts encrypted text using the RSA private key.
     *
     * @param text The encrypted text (Base64 encoded)
     * @param key  Not used (kept for interface consistency)
     * @return Original decrypted text
     */
    @Override
    public String decrypt(String text, String key) {
        try {
            // Create RSA cipher instance
            Cipher cipher = Cipher.getInstance("RSA");

            // Initialize cipher with PRIVATE key for decryption
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

            // Decode Base64 back to bytes
            byte[] decodedBytes = Base64.getDecoder().decode(text);

            // Perform decryption
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            // Convert bytes back to string
            return new String(decryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException("RSA decryption failed: " + e.getMessage());
        }
    }
}