package service;

/**
 * VigenereCipher implements the CipherService interface using the Vigenère Cipher.
 *
 * This cipher improves upon the Caesar Cipher by using a keyword to determine
 * the shift for each letter in the text.
 *
 * Example:
 * - Text: HELLO
 * - Key: KEY
 * - Each letter is shifted based on corresponding key letter
 *
 * Notes:
 * - Only alphabetic characters are encrypted
 * - Non-letter characters (spaces, numbers, symbols) remain unchanged
 * - Key repeats if it is shorter than the text
 */
public class VigenereCipher implements CipherService {

    /**
     * Encrypts the given plain text using the Vigenère Cipher.
     *
     * @param text The plain text to encrypt
     * @param key  The keyword used for shifting
     * @return Encrypted text
     */
    @Override
    public String encrypt(String text, String key) {
        return transform(text, key, true);
    }

    /**
     * Decrypts the given encrypted text using the Vigenère Cipher.
     *
     * @param text The encrypted text
     * @param key  The keyword used during encryption
     * @return Decrypted (original) text
     */
    @Override
    public String decrypt(String text, String key) {
        return transform(text, key, false);
    }

    /**
     * Core transformation logic used for both encryption and decryption.
     *
     * Process:
     * - Loop through each character in the text
     * - If character is a letter:
     *      - Convert to numeric value (0–25)
     *      - Use corresponding key character for shift
     *      - Apply shift (add for encrypt, subtract for decrypt)
     *      - Convert back to character
     * - If not a letter:
     *      - Keep it unchanged
     *
     * @param text      Input text
     * @param key       Keyword used for shifting
     * @param isEncrypt True for encryption, false for decryption
     * @return Transformed text
     */
    private String transform(String text, String key, boolean isEncrypt) {

        StringBuilder result = new StringBuilder();

        // Normalize key to lowercase for consistent processing
        String cleanKey = key.toLowerCase();

        // Tracks position within the key
        int keyIndex = 0;

        // Loop through each character of the input text
        for (int i = 0; i < text.length(); i++) {

            char currentChar = text.charAt(i);

            if (Character.isLetter(currentChar)) {

                // Determine base ASCII value (A or a)
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';

                // Convert text character to numeric value (0–25)
                int textValue = Character.toLowerCase(currentChar) - 'a';

                // Get corresponding key character value (0–25)
                int keyValue = cleanKey.charAt(keyIndex % cleanKey.length()) - 'a';

                int shiftedValue;

                if (isEncrypt) {
                    // Encryption: shift forward
                    shiftedValue = (textValue + keyValue) % 26;
                } else {
                    // Decryption: shift backward
                    shiftedValue = (textValue - keyValue + 26) % 26;
                }

                // Convert numeric value back to character
                char shiftedChar = (char) (shiftedValue + base);

                result.append(shiftedChar);

                // Move to next key character
                keyIndex++;

            } else {
                // Non-letter characters remain unchanged
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}