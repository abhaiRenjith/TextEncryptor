package factory;

import service.CipherService;
import service.AesCipher;
import service.CaesarCipher;
import service.RsaCipher;
import service.VigenereCipher;
import service.XorCipher;

/**
 * CipherFactory is responsible for creating and returning
 * the appropriate cipher object based on user selection.
 *
 * This class implements the Factory Design Pattern.
 *
 * Benefits:
 * - Decouples object creation from usage
 * - Makes it easy to add new algorithms
 * - Keeps code clean and maintainable
 *
 * Notes:
 * - RSA is stored as a single instance because it uses
 *   a generated key pair that must persist across operations
 */
public class CipherFactory {

    // Single RSA instance (important for consistent encryption/decryption)
    private RsaCipher rsaCipher;

    /**
     * Constructor initializes the RSA cipher once.
     */
    public CipherFactory() {
        rsaCipher = new RsaCipher();
    }

    /**
     * Returns the appropriate cipher implementation
     * based on the algorithm choice.
     *
     * @param algorithmChoice Integer representing user selection
     * @return Corresponding CipherService implementation
     * @throws IllegalArgumentException if choice is invalid
     */
    public CipherService getCipher(int algorithmChoice) {

        switch (algorithmChoice) {

            case 1:
                // Caesar Cipher (basic shift cipher)
                return new CaesarCipher();

            case 2:
                // Vigenere Cipher (keyword-based shift cipher)
                return new VigenereCipher();

            case 3:
                // XOR Cipher (bitwise encryption)
                return new XorCipher();

            case 4:
                // AES Cipher (modern symmetric encryption)
                return new AesCipher();

            case 5:
                // RSA Cipher (asymmetric encryption)
                // Uses same instance to preserve key pair
                return rsaCipher;

            default:
                // Handle invalid user input
                throw new IllegalArgumentException("Invalid algorithm choice.");
        }
    }
}