package com.kwizera.javaamalilabs03bankmgtsystem.models;

import com.kwizera.javaamalilabs03bankmgtsystem.services.BankServices;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account implements BankServices {
    String accountNumber;
    String accountHolder;
    double balance;
    String accountType;

    private Transaction head = null;
    private Transaction tail = null;


    Account(String accountHolder, String accountNumber, Double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountType = accountType;
    }

    public abstract boolean withdraw(double amount);

    public void deposit(double amount) {
        balance += amount;
        recordTransaction("deposit", amount);
    }

    public ArrayList<String> printHistory() {
        Transaction current = head;
        ArrayList<String> history = new ArrayList<>();

        while (current != null) {
            history.add(current.toString());
            current = current.next;
        }

        return history;
    }

    public ArrayList<String> printHistory(int N) {
        Transaction current = tail;
        ArrayList<String> history = new ArrayList<>();

        int counter = N - 1;
        while (counter >= 0 && current != null) {
            history.add(current.toString());
            current = current.prev;
            counter--;
        }

        return history;
    }

    public void recordTransaction(String type, double amount) {
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction newTransaction = new Transaction(type, amount, timestamp, null, null);
        if (head == null) {
            head = newTransaction;
            tail = newTransaction;
            return;
        } else {
            Transaction current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newTransaction;
            newTransaction.prev = current;
            newTransaction.next = null;
            tail = newTransaction;
        }

    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getInfo() {
        return accountHolder + " - " + accountHolder + " | Balance: " + balance;
    }

}
