# LAB: Bank Account Management System
## Overview
A simple bank account management system built using JavaFX, applying key Object-Oriented Programming (OOP) concepts like inheritance, abstraction, composition, and interfaces.
The system supports account creation, deposits, withdrawals, and transaction history viewing.

## Features
- Create Savings, Current, or Fixed Deposit Accounts
- View account details
- Deposit and withdraw money (rules varies according to account type)
- View transaction history
- Input validation with visual feedback
- Scene switching with persistent session data

## Stack
- Java 21
- JavaFX (FXML + Scenebuilder)
- Maven (for dependency management)

## OOP applications
- Abstraction: the Account class is abstract, defining common attributes such as account balance and behaviours such `deposit()` and `withdraw()` of a bank account.
- Inheritance: `SavingsAccount`, `CurrentAccount` and `FixedDepositAccount` extend the abstract `Account`.
- Composition: `SessionManager` class contains an `Account` object to represent the logged-in user, enforcing a session 'has-a' account relationship.
- Interface: The `Account` implements method signatures contained in the `BankServices` interface.

## Extra
- Transaction history implemented using a doubly linked list
- by default the overdraft limit for current accounts is set to 100,000 F
- by default the minimum balance for savings accounts is set to 50,000 F
- by default the maturity period is set to 6 months with 8.5% interest rate for fixed deposit accounts

## How to run
- Clone the project
- Open in intelliJ IDEA or another JavaFX-Compatible IDE
- Maven adds dependencies automatically
- Run the `Main` class
- 👍

