# Text Encryptor (Java)

<img width="945" height="646" alt="image" src="https://github.com/user-attachments/assets/20428a10-f8ab-4158-8e23-04c25c37c6b8" />


A modular text encryption application built in Java, implementing multiple classical and modern cryptographic algorithms with a graphical user interface and clean object-oriented architecture.

---

## Overview

This project provides a unified platform to explore and apply different encryption techniques, ranging from classical ciphers to modern cryptographic standards. It was developed as part of a cybersecurity learning process after setting up a personal security lab environment.

The system is designed with extensibility in mind, allowing new encryption algorithms to be added with minimal changes.

---

## Features

- Multiple encryption algorithms:
  - Caesar Cipher
  - Vigenère Cipher
  - XOR Cipher
  - AES (Advanced Encryption Standard)
  - RSA (Asymmetric Encryption)

- Graphical User Interface (GUI)
  - Dark theme (black and purple)
  - Clean and responsive layout
  - Dynamic input handling (e.g., RSA key handling)

- Object-Oriented Design
  - Interface-based architecture
  - Factory Design Pattern
  - Separation of concerns

- Built-in Testing System
  - Runs multiple test cases per algorithm
  - Verifies encryption and decryption correctness

---

## Cybersecurity Context

This project was created after setting up a personal cybersecurity lab using tools such as:

- Kali Linux  
- Remote access setup (SSH, Tailscale)  
- Network analysis tools  

The goal was to strengthen understanding of:

- Cryptographic principles  
- Differences between classical and modern encryption  
- Practical implementation of secure systems  
- Software design patterns in security-focused applications  

---

## Project Structure


src/
├── app/ → Application entry point
├── gui/ → Graphical user interface
├── menu/ → Console interface
├── factory/ → CipherFactory (object creation)
├── service/ → Cipher implementations
├── exception/ → Test runner
---

## How It Works

All encryption algorithms implement a shared interface:

```java
public interface CipherService {
    String encrypt(String text, String key);
    String decrypt(String text, String key);
}
```
The CipherFactory selects the appropriate algorithm at runtime based on user input, keeping the system modular and scalable.

Algorithms Overview
Algorithm	Type	Description
Caesar	Classical	Fixed letter shift
Vigenère	Classical	Keyword-based shifting
XOR	Bitwise	Simple reversible encryption
AES	Symmetric	Modern secure encryption
RSA	Asymmetric	Public/private key encryption
Notes
RSA is intended for small data, not large text
AES is used for bulk encryption
XOR is included for educational purposes and is not secure
How to Run

Run GUI:

Run Main.java

Run Tests:

Run CipherTestRunner.java
Testing

The test runner:

Executes 12 test cases per algorithm
Validates encryption and decryption cycles
Throws an error if any test fails
Technologies Used
Java
Java Swing
Java Cryptography Architecture (JCA)
Object-Oriented Programming
Future Improvements
File encryption support
Hybrid encryption (RSA + AES)
Clipboard integration
UI enhancements (FlatLaf)
Author

Developed as part of a computer science and cybersecurity learning project focused on encryption systems and secure software design.
