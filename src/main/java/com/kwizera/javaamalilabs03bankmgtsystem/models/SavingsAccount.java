package com.kwizera.javaamalilabs03bankmgtsystem.models;

public class SavingsAccount extends Account {
    private final double minimumBalance;

    public SavingsAccount(String accountHolder, String accountNumber, Double balance, Double minimumBalance) {
        super(accountHolder, accountNumber, balance, "savings");
        this.minimumBalance = minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= (balance - minimumBalance)) {
            this.balance -= amount;
            recordTransaction("withdraw", amount);
            return true;
        }
        return false;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

}
