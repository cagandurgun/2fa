User Authentication System

Overview
The User Authentication System is a simple Java application that allows users to create an account, log in using a username and password, and complete a two-factor authentication (2FA) process. This application employs SHA-256 for password hashing and includes validation checks to ensure password strength. It also generates a 6-digit 2FA code that users need to enter to complete the login process.

Features
User Creation: Users can create new accounts with unique usernames and strong passwords.
User Login: Users can log in with their username and password.
Two-Factor Authentication: A 6-digit code is generated and stored in a file. Users must enter this code to complete the login process.
Password Strength Validation: Passwords must be at least 8 characters long and must not contain Turkish characters.
Secure Password Storage: Passwords are hashed using SHA-256 with a random salt.
Directory Structure
css
Kodu kopyala
.
├── CustomCrypto.java
├── Main.java
├── TwoFactorCodeGenerator.java
├── UserAuthenticationSystem.java
├── users.txt
├── passwords.txt
├── two_factor_codes.txt
└── README.md
Classes and Files
1. Main.java

This is the entry point of the application. It initializes and runs the UserAuthenticationSystem.

java
Kodu kopyala
// Main.java içeriği buraya kopyalanacak
2. UserAuthenticationSystem.java

Handles the main functionalities of user creation, user login, and two-factor authentication.

java
Kodu kopyala
// UserAuthenticationSystem.java içeriği buraya kopyalanacak
3. TwoFactorCodeGenerator.java

Generates and verifies 6-digit 2FA codes.

java
Kodu kopyala
// TwoFactorCodeGenerator.java içeriği buraya kopyalanacak
4. CustomCrypto.java

Handles password hashing and verification using SHA-256.

java
Kodu kopyala
// CustomCrypto.java içeriği buraya kopyalanacak
