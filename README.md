# User Authentication System

## Overview
The User Authentication System is a simple Java application that facilitates user account creation, login, and two-factor authentication (2FA) process. It utilizes SHA-256 for password hashing, includes password strength validation, and implements a two-factor authentication system generating a 6-digit code.

## Features
- **User Creation:** Users can create accounts with unique usernames and strong passwords.
- **User Login:** Users can log in using their username and password.
- **Two-Factor Authentication (2FA):** Generates and verifies a 6-digit code stored in a file.
- **Password Strength Validation:** Passwords must be at least 8 characters long and devoid of Turkish characters.
- **Secure Password Storage:** Passwords are hashed using SHA-256 with a random salt.

## Classes and Files

1. **Main.java:** Entry point of the application. Initializes and runs the UserAuthenticationSystem.
2. **UserAuthenticationSystem.java:** Manages user creation, login, and two-factor authentication functionalities.
3. **TwoFactorCodeGenerator.java:** Generates and verifies 6-digit 2FA codes.
4. **CustomCrypto.java:** Handles password hashing and verification using SHA-256.

## Usage
1. Clone the repository.
2. Compile the Java files.
3. Run Main.java to start the User Authentication System.

## Contribution
Contributions are welcome. Feel free to submit issues and pull requests.
