# Bank Application System

A simple bank application built in **Java** using **Swing** for the GUI. The application allows users to perform basic banking operations like depositing, withdrawing, checking balance, and logging in.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The **Bank Application System** is designed to simulate the functionality of a real-world banking system. The application allows users to create an account, log in, and perform operations such as depositing money, withdrawing funds, and checking account balances.

The system uses a **Graphical User Interface (GUI)** built with **Swing**, which provides an easy-to-use interface for interacting with the bank.

## Features

- **Login/Sign-up System**: Users can securely log in to their accounts and perform transactions.
- **Deposit**: Users can deposit funds into their accounts.
- **Withdraw**: Users can withdraw funds from their accounts.
- **Check Balance**: Users can check their account balance at any time.
- **Database Integration**: The application is connected to a database for storing account information and transaction history.
  
## Installation

### Prerequisites

Before you begin, ensure that you have met the following requirements:

- **Java Development Kit (JDK) 21** or higher is installed on your machine.
- **IntelliJ IDEA** or another Java IDE is installed for development.

### Steps to Install

1. **Clone the repository:**

    You can clone the repository using the following command:

    ```bash
    git clone https://github.com/your-username/bank-application.git
    ```

2. **Open the project in your IDE:**

    Open the cloned project folder in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).

3. **Build and run the project:**

    To run the project, simply execute the `BankGUI` class, which contains the main method that launches the GUI application.

    ```java
    public static void main(String[] args) {
        new BankGUI();
    }
    ```

4. **Configure the database** (Optional):

    If your project connects to a real database (e.g., MySQL, SQLite), you may need to configure the connection strings and credentials in the code. You can edit these details in the `DatabaseManager` class.

## Usage

Once the application is launched, you will see the **Dashboard** screen. Here's a quick guide on how to use the features:

1. **Login Screen**: The user will be prompted to enter their credentials (username and password) to log in to their account.
2. **Dashboard**: After logging in, the user will be presented with options to:
    - Deposit money into their account.
    - Withdraw money from their account.
    - Check the account balance.

### Examples

After clicking on the "Deposit" button, the user will be prompted to enter the sender and recipient account details and the amount to deposit. Similarly, the withdrawal process asks for a PIN and withdrawal amount.

## Contributing

We welcome contributions from the community! Here are some ways you can contribute:

- **Bug Reports**: If you find any bugs or issues, please create a new issue in the GitHub issues section.
- **Feature Requests**: If you have any ideas for new features or improvements, feel free to open a feature request.
- **Pull Requests**: You can fork the repository, make changes, and submit a pull request. We will review and merge it.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
