package exception;

import service.*;

/**
 * CipherTestRunner is a standalone test utility for verifying
 * the correctness of all encryption algorithms.
 *
 * It performs:
 * - Encryption → Decryption cycle
 * - Compares output with original text
 * - Runs 12 test cases per algorithm
 *
 * If any test fails → throws RuntimeException
 * If all pass → prints success message
 *
 * Note:
 * - RSA uses internally generated keys
 * - Other ciphers use user-provided keys
 */
public class CipherTestRunner {

    /**
     * Entry point for running tests independently.
     */
    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * Runs all cipher tests sequentially.
     */
    public static void runAllTests() {
        System.out.println("=== RUNNING ALL CIPHER TESTS ===");

        testCaesarCipher();
        testVigenereCipher();
        testXorCipher();
        testAesCipher();
        testRsaCipher();

        System.out.println();
        System.out.println("=== ALL TESTS PASSED ===");
    }

    /**
     * Tests Caesar Cipher with 12 inputs.
     */
    private static void testCaesarCipher() {
        CipherService cipher = new CaesarCipher();
        runStandardTests("CaesarCipher", cipher, "3");
    }

    /**
     * Tests Vigenere Cipher with 12 inputs.
     */
    private static void testVigenereCipher() {
        CipherService cipher = new VigenereCipher();
        runStandardTests("VigenereCipher", cipher, "key");
    }

    /**
     * Tests XOR Cipher with 12 inputs.
     */
    private static void testXorCipher() {
        CipherService cipher = new XorCipher();
        runStandardTests("XorCipher", cipher, "secret");
    }

    /**
     * Tests AES Cipher with 12 inputs.
     */
    private static void testAesCipher() {
        CipherService cipher = new AesCipher();
        runStandardTests("AesCipher", cipher, "mypassword");
    }

    /**
     * Tests RSA Cipher separately (no external key required).
     */
    private static void testRsaCipher() {
        RsaCipher cipher = new RsaCipher();

        String[] testInputs = getTestInputs();

        for (int i = 0; i < testInputs.length; i++) {
            String original = testInputs[i];

            String encrypted = cipher.encrypt(original, "");
            String decrypted = cipher.decrypt(encrypted, "");

            if (!original.equals(decrypted)) {
                throw new RuntimeException(
                        "RsaCipher FAILED on test " + (i + 1) +
                                "\nOriginal: " + original +
                                "\nEncrypted: " + encrypted +
                                "\nDecrypted: " + decrypted
                );
            }

            System.out.println("RsaCipher test " + (i + 1) + " passed");
        }
    }

    /**
     * Reusable method for testing standard ciphers.
     */
    private static void runStandardTests(String name, CipherService cipher, String key) {

        String[] testInputs = getTestInputs();

        for (int i = 0; i < testInputs.length; i++) {
            String original = testInputs[i];

            String encrypted = cipher.encrypt(original, key);
            String decrypted = cipher.decrypt(encrypted, key);

            if (!original.equals(decrypted)) {
                throw new RuntimeException(
                        name + " FAILED on test " + (i + 1) +
                                "\nOriginal: " + original +
                                "\nEncrypted: " + encrypted +
                                "\nDecrypted: " + decrypted
                );
            }

            System.out.println(name + " test " + (i + 1) + " passed");
        }
    }

    /**
     * Provides a shared set of test inputs.
     */
    private static String[] getTestInputs() {
        return new String[] {
                "HelloWorld",
                "Java",
                "EncryptMe",
                "abcxyz",
                "HELLO",
                "hello world",
                "Attack at Dawn",
                "Text123",
                "Cipher Test",
                "OpenAI",
                "SecurityCheck",
                "FinalTest"
        };
    }
}