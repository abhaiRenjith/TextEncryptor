package service;

/**
 * CipherService defines a common interface for all encryption algorithms.
 *
 * Any cipher class (Caesar, Vigenere, XOR, AES, RSA, etc.)
 * must implement this interface so they can be used interchangeably.
 *
 * This allows:
 * - Consistent method calls (encrypt/decrypt)
 * - Easy integration with factory pattern
 * - Clean and scalable architecture
 */
public interface CipherService {

    /**
     * Encrypts the given plain text using a specific cipher algorithm.
     *
     * @param text The input plain text to encrypt
     * @param key  The key used for encryption (may be ignored by some algorithms like RSA)
     * @return Encrypted text
     */
    String encrypt(String text, String key);

    /**
     * Decrypts the given encrypted text using the same algorithm and key.
     *
     * @param text The encrypted text
     * @param key  The key used during encryption (must match)
     * @return Decrypted (original) text
     */
    String decrypt(String text, String key);
}