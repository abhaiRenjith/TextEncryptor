package gui;

import service.CipherService;
import factory.CipherFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * TextEncryptorGui provides a graphical user interface (GUI)
 * for encrypting and decrypting text using multiple algorithms.
 *
 * Features:
 * - Supports Caesar, Vigenere, XOR, AES, and RSA ciphers
 * - Dark mode toggle (black + purple theme)
 * - Input and output panels
 * - Dynamic key field (hidden for RSA)
 *
 * Notes:
 * - Uses CipherFactory to create cipher objects
 * - Input fields are kept white for readability in dark mode
 */

public class TextEncryptorGui extends JFrame {

    private JComboBox<String> modeComboBox;
    private JComboBox<String> algorithmComboBox;
    private JTextArea inputTextArea;
    private JTextField keyField;
    private JTextArea outputTextArea;
    private JLabel keyLabel;
    private JCheckBox darkModeToggle;

    private CipherFactory cipherFactory;

    // Dark theme colors
    private final Color darkBackground = new Color(12, 12, 18);
    private final Color darkCard = new Color(24, 24, 35);
    private final Color darkAccent = new Color(138, 43, 226);
    private final Color darkText = new Color(235, 235, 245);
    private final Color darkBorder = new Color(85, 45, 130);

    // Light theme colors
    private final Color lightBackground = new Color(245, 245, 250);
    private final Color lightCard = new Color(255, 255, 255);
    private final Color lightAccent = new Color(114, 57, 191);
    private final Color lightText = new Color(40, 40, 55);
    private final Color lightBorder = new Color(180, 160, 220);

    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel controlsPanel;
    private JPanel keyPanel;
    private JPanel buttonPanel;

    private JButton processButton;
    private JButton clearButton;

    public TextEncryptorGui() {
        cipherFactory = new CipherFactory();

        setTitle("Text Encryptor");
        setSize(950, 650);
        setMinimumSize(new Dimension(850, 580));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        applyTheme(true);

        setVisible(true);
    }

    private void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("Text Encryptor");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel subtitleLabel = new JLabel("Encrypt and decrypt text using multiple algorithms");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel titleWrapper = new JPanel();
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.Y_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.add(titleLabel);
        titleWrapper.add(Box.createVerticalStrut(5));
        titleWrapper.add(subtitleLabel);

        darkModeToggle = new JCheckBox("Dark Mode", true);
        darkModeToggle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        darkModeToggle.setFocusPainted(false);
        darkModeToggle.setOpaque(false);
        darkModeToggle.addActionListener(e -> applyTheme(darkModeToggle.isSelected()));

        headerPanel.add(titleWrapper, BorderLayout.WEST);
        headerPanel.add(darkModeToggle, BorderLayout.EAST);

        controlsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        controlsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        modeComboBox = new JComboBox<>(new String[]{"Encrypt", "Decrypt"});
        algorithmComboBox = new JComboBox<>(new String[]{
                "Caesar Cipher",
                "Vigenere Cipher",
                "XOR Cipher",
                "AES Cipher",
                "RSA Cipher"
        });

        controlsPanel.add(createLabeledComponent("Mode", modeComboBox));
        controlsPanel.add(createLabeledComponent("Algorithm", algorithmComboBox));

        keyField = new JTextField();
        keyLabel = new JLabel("Key");
        keyPanel = createLabeledComponent("Key", keyField);
        controlsPanel.add(keyPanel);

        JLabel infoLabel = new JLabel("RSA does not need a manual key.");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        controlsPanel.add(infoPanel);

        inputTextArea = createStyledTextArea();
        outputTextArea = createStyledTextArea();
        outputTextArea.setEditable(false);

        JPanel inputPanel = createTextPanel("Input Text", inputTextArea);
        JPanel outputPanel = createTextPanel("Output Text", outputTextArea);

        contentPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        contentPanel.add(inputPanel);
        contentPanel.add(outputPanel);

        processButton = createStyledButton("Process");
        clearButton = createStyledButton("Clear");

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.add(clearButton);
        buttonPanel.add(processButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 15));
        bottomPanel.add(controlsPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        algorithmComboBox.addActionListener(e -> toggleKeyField());
        processButton.addActionListener(e -> processText());
        clearButton.addActionListener(e -> clearFields());

        toggleKeyField();
    }

    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        return panel;
    }

    private JPanel createTextPanel(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTextArea createStyledTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textArea.setMargin(new Insets(14, 14, 14, 14));
        textArea.setCaretColor(Color.BLACK);
        return textArea;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 22, 12, 22));
        return button;
    }

    private void toggleKeyField() {
        int algorithmChoice = algorithmComboBox.getSelectedIndex() + 1;
        boolean needsKey = algorithmChoice != 5;

        keyPanel.setVisible(needsKey);
        revalidate();
        repaint();
    }

    private void processText() {
        try {
            int algorithmChoice = algorithmComboBox.getSelectedIndex() + 1;
            String inputText = inputTextArea.getText().trim();
            String key = keyField.getText();

            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter input text.");
                return;
            }

            if (algorithmChoice != 5 && key.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a key.");
                return;
            }

            CipherService cipher = cipherFactory.getCipher(algorithmChoice);
            String selectedMode = (String) modeComboBox.getSelectedItem();
            String result;

            if ("Encrypt".equals(selectedMode)) {
                result = cipher.encrypt(inputText, key);
            } else {
                result = cipher.decrypt(inputText, key);
            }

            outputTextArea.setText(result);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Operation failed: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {
        inputTextArea.setText("");
        keyField.setText("");
        outputTextArea.setText("");
    }

    private void applyTheme(boolean darkMode) {
        Color background = darkMode ? darkBackground : lightBackground;
        Color card = darkMode ? darkCard : lightCard;
        Color accent = darkMode ? darkAccent : lightAccent;
        Color text = darkMode ? darkText : lightText;
        Color border = darkMode ? darkBorder : lightBorder;

        mainPanel.setBackground(background);
        headerPanel.setBackground(card);
        contentPanel.setBackground(background);
        controlsPanel.setBackground(card);
        keyPanel.setBackground(card);
        buttonPanel.setBackground(background);

        styleAllPanels(mainPanel, background, card);

        styleComboBox(modeComboBox, border);
        styleComboBox(algorithmComboBox, border);
        styleTextField(keyField, border);
        styleTextArea(inputTextArea, border);
        styleTextArea(outputTextArea, border);

        styleButton(processButton, accent, Color.WHITE);
        styleButton(clearButton, new Color(70, 70, 90), Color.WHITE);

        updateLabelColors(mainPanel, text);
        darkModeToggle.setForeground(text);

        repaint();
    }

    private void styleAllPanels(Container container, Color background, Color card) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel panel) {
                panel.setBackground(card);
                styleAllPanels(panel, background, card);
            }
        }

        mainPanel.setBackground(background);
        contentPanel.setBackground(background);
        buttonPanel.setBackground(background);
    }

    private void updateLabelColors(Container container, Color textColor) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel label) {
                label.setForeground(textColor);
            } else if (component instanceof Container inner) {
                updateLabelColors(inner, textColor);
            }
        }
    }

    private void styleComboBox(JComboBox<String> comboBox, Color border) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createLineBorder(border, 1));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                setBackground(Color.WHITE);
                setForeground(Color.BLACK);

                if (isSelected) {
                    setBackground(new Color(200, 180, 255));
                    setForeground(Color.BLACK);
                }

                return this;
            }
        });
    }

    private void styleTextField(JTextField textField, Color border) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setCaretColor(Color.BLACK);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
    }

    private void styleTextArea(JTextArea textArea, Color border) {
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setCaretColor(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    }
}