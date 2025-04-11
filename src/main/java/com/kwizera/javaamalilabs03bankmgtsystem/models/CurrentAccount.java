package com.kwizera.javaamalilabs03bankmgtsystem.models;

public class CurrentAccount extends Account {
    private final double overdraftLimit;

    public CurrentAccount(String accountHolder, String accountNumber, double balance, double overdraftLimit) {
        super(accountHolder, accountNumber, balance, "current");
        this.overdraftLimit = overdraftLimit;
    }


    @Override
    public boolean withdraw(double amount) {
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
