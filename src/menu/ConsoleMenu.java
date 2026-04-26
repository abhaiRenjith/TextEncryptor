package menu;

import factory.CipherFactory;
import service.CipherService;
import java.util.Scanner;

/**
 * ConsoleMenu handles the text-based user interface for the Text Encryptor project.
 *
 * This class is responsible for:
 * - Displaying menu options
 * - Reading user input
 * - Letting the user choose encrypt/decrypt
 * - Letting the user choose an algorithm
 * - Passing the request to the correct cipher
 *
 * Notes:
 * - Uses CipherFactory to create the selected cipher
 * - Keeps running until the user chooses Exit
 * - Includes basic input validation and error handling
 */
public class ConsoleMenu {

    // Scanner used to read user input from console
    private Scanner scanner;

    // Factory used to create cipher objects
    private CipherFactory cipherFactory;

    /**
     * Constructor: initializes scanner and cipher factory.
     */
    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        cipherFactory = new CipherFactory();
    }

    /**
     * Starts one cycle of the console menu.
     *
     * Flow:
     * 1. Show main menu
     * 2. Read user choice
     * 3. Ask for algorithm
     * 4. Ask for text and key
     * 5. Perform encryption or decryption
     *
     * @return false if user chooses Exit, true otherwise
     */
    public boolean start() {
        try {
            // Display main menu
            System.out.println("=== TEXT ENCRYPTOR ===");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            // Read main menu choice
            int userChoice = Integer.parseInt(scanner.nextLine());

            // Exit program if user chooses option 3
            if (userChoice == 3) {
                return false;
            }

            // Reject invalid menu choices
            if (userChoice != 1 && userChoice != 2) {
                System.out.println("Invalid menu option.");
                System.out.println();
                return true;
            }

            // Display available cipher algorithms
            System.out.println("Choose algorithm:");
            System.out.println("1. Caesar Cipher");
            System.out.println("2. Vigenere Cipher");
            System.out.println("3. XOR Cipher");
            System.out.println("4. AES Cipher");
            System.out.print("Enter algorithm choice: ");

            // Read algorithm choice
            int algorithmChoice = Integer.parseInt(scanner.nextLine());

            // Ask factory for the selected cipher
            CipherService cipher = cipherFactory.getCipher(algorithmChoice);

            // Read input text from user
            System.out.print("Enter text: ");
            String inputText = scanner.nextLine();

            // Read encryption/decryption key
            System.out.print("Enter key: ");
            String key = scanner.nextLine();

            // Perform encryption or decryption depending on user choice
            if (userChoice == 1) {
                String encryptedText = cipher.encrypt(inputText, key);
                System.out.println("Encrypted text: " + encryptedText);
            } else {
                String decryptedText = cipher.decrypt(inputText, key);
                System.out.println("Decrypted text: " + decryptedText);
            }

            System.out.println();
            return true;

        } catch (NumberFormatException e) {
            // Handles invalid numeric input for menu options
            System.out.println("Please enter numbers only for menu choices.");
            System.out.println();
            return true;

        } catch (IllegalArgumentException e) {
            // Handles invalid algorithm choice or invalid key-related arguments
            System.out.println(e.getMessage());
            System.out.println();
            return true;

        } catch (Exception e) {
            // Handles any unexpected errors
            System.out.println("Something went wrong: " + e.getMessage());
            System.out.println();
            return true;
        }
    }
}