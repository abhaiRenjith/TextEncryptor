package service;

/**
 * CaesarCipher implements the CipherService interface using the classic Caesar Cipher algorithm.
 *
 * This cipher works by shifting each letter in the text by a fixed number of positions.
 *
 * Example:
 * - Shift = 3
 * - "abc" → "def"
 *
 * Notes:
 * - Only letters (A–Z, a–z) are shifted
 * - Non-letter characters (spaces, numbers, symbols) remain unchanged
 * - Decryption is done by reversing the shift
 */
public class CaesarCipher implements CipherService {

    /**
     * Encrypts the given plain text using Caesar Cipher.
     *
     * @param text The input plain text
     * @param key  The shift value (as a string, e.g., "3")
     * @return Encrypted text
     */
    @Override
    public String encrypt(String text, String key) {
        // Convert key from String to integer shift value
        int shift = Integer.parseInt(key);

        // Apply forward shift
        return transform(text, shift);
    }

    /**
     * Decrypts the given encrypted text using Caesar Cipher.
     *
     * @param text The encrypted text
     * @param key  The shift value used during encryption
     * @return Decrypted (original) text
     */
    @Override
    public String decrypt(String text, String key) {
        int shift = Integer.parseInt(key);

        // Reverse the shift for decryption
        return transform(text, -shift);
    }

    /**
     * Core transformation logic used for both encryption and decryption.
     *
     * Process:
     * - Iterate through each character
     * - If uppercase → shift within 'A' to 'Z'
     * - If lowercase → shift within 'a' to 'z'
     * - If not a letter → leave unchanged
     *
     * @param text  The input text
     * @param shift The shift value (positive for encrypt, negative for decrypt)
     * @return Transformed text
     */
    private String transform(String text, int shift) {

        StringBuilder result = new StringBuilder();

        // Loop through each character in the text
        for (char c : text.toCharArray()) {

            if (Character.isUpperCase(c)) {
                // Base ASCII value for uppercase letters
                char base = 'A';

                // Apply shift with wrap-around using modulo 26
                char shifted = (char) ((c - base + shift + 26) % 26 + base);

                result.append(shifted);

            } else if (Character.isLowerCase(c)) {
                // Base ASCII value for lowercase letters
                char base = 'a';

                // Apply shift with wrap-around
                char shifted = (char) ((c - base + shift + 26) % 26 + base);

                result.append(shifted);

            } else {
                // Keep non-letter characters unchanged
                result.append(c);
            }
        }

        return result.toString();
    }
}