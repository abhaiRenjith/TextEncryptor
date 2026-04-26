package service;

import java.util.Base64;

/**
 * XorCipher implements the CipherService interface using a simple XOR-based encryption.
 *
 * XOR (Exclusive OR) is a bitwise operation where:
 * - 0 ^ 0 = 0
 * - 1 ^ 1 = 0
 * - 0 ^ 1 = 1
 * - 1 ^ 0 = 1
 *
 * Key idea:
 * - Applying XOR twice with the same key returns the original value
 *   (A ^ B ^ B = A)
 *
 * Notes:
 * - Same logic is used for encryption and decryption
 * - Key is repeated if shorter than the text
 * - Output is encoded in Base64 for readability
 * - This is NOT secure for real-world use (educational purpose)
 */
public class XorCipher implements CipherService {

    /**
     * Encrypts the given text using XOR operation.
     *
     * @param text The plain text to encrypt
     * @param key  The key used for XOR operation
     * @return Encrypted text encoded in Base64
     */
    @Override
    public String encrypt(String text, String key) {

        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be empty");
        }

        // Convert text and key to byte arrays
        byte[] textBytes = text.getBytes();
        byte[] keyBytes = key.getBytes();

        // Array to store result
        byte[] resultBytes = new byte[textBytes.length];

        // Perform XOR operation byte-by-byte
        for (int i = 0; i < textBytes.length; i++) {
            resultBytes[i] = (byte) (textBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        // Encode result to Base64 for readable output
        return Base64.getEncoder().encodeToString(resultBytes);
    }

    /**
     * Decrypts the given encrypted text using XOR operation.
     *
     * Since XOR is reversible, this uses the same logic as encryption.
     *
     * @param text The encrypted text (Base64 encoded)
     * @param key  The same key used during encryption
     * @return Decrypted original text
     */
    @Override
    public String decrypt(String text, String key) {

        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be empty");
        }


        // Decode Base64 back to original XOR-ed bytes
        byte[] encryptedBytes = Base64.getDecoder().decode(text);

        byte[] keyBytes = key.getBytes();

        // Array to store decrypted result
        byte[] resultBytes = new byte[encryptedBytes.length];

        // Apply XOR again with same key to recover original text
        for (int i = 0; i < encryptedBytes.length; i++) {
            resultBytes[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        // Convert byte array back to string
        return new String(resultBytes);
    }
}