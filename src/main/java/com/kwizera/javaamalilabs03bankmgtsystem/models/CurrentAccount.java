package com.kwizera.javaamalilabs03bankmgtsystem.models;

public class CurrentAccount extends Account {
    private final double overdraftLimit;

    // uses constructor chaining to create an instance of a current account
    public CurrentAccount(String accountHolder, String accountNumber, double balance, double overdraftLimit) {
        super(accountHolder, accountNumber, balance, "current");
        this.overdraftLimit = overdraftLimit;
    }

    // overriding the abstract withdraw method to define it respective to a current account
    @Override
    public boolean withdraw(double amount) {
        // restricting withdraw operations to not exceed overdraft limits
        if (amount <= (balance + overdraftLimit)) {
            balance -= amount;
            recordTransaction("withdraw", amount);
            return true;
        }
        return false;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
