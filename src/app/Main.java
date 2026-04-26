package app;

import gui.TextEncryptorGui;

import javax.swing.SwingUtilities;

/**
 * Main is the entry point of the Text Encryptor application.
 *
 * Responsibilities:
 * - Launch the GUI application
 * - Ensure the GUI runs on the Event Dispatch Thread (EDT)
 *
 * Notes:
 * - Swing applications must run on the EDT for thread safety
 * - This class does not handle logic — only startup
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Ensure GUI is created on the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new TextEncryptorGui();
        });
    }
}