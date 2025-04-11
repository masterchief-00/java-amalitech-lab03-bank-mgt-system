package com.kwizera.javaamalilabs03bankmgtsystem.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FixedDepositAccount extends Account {
    private final LocalDateTime maturityDate;
    private final double interestRate;

    public FixedDepositAccount(String accountHolder, String accountNumber, double balance, LocalDateTime maturityDate, double interestRate) {
        super(accountHolder, accountNumber, balance, "fixed_deposit");
        this.maturityDate = maturityDate;
        this.interestRate = interestRate;
    }

    @Override
    public boolean withdraw(double amount) {
        if (isMature() && amount <= balance) {
            makeMature();
            balance = 0;
            recordTransaction("withdraw", amount);
            return true;
        }
        return false;
    }

    @Override
    public void deposit(double amount) {
        if (isMature()) {
            balance += amount;
            recordTransaction("deposit", amount);
        }
    }

    public void makeMature() {
        double interestAmount = calculateInterest();
        balance += interestAmount;
    }

    public double calculateInterest() {
        long months = Math.abs(ChronoUnit.MONTHS.between(maturityDate, LocalDateTime.now()));
        return ((balance * interestRate * months) / 100);
    }

    public boolean isMature() {
        return LocalDateTime.now().isAfter(maturityDate);
    }

    public long maturesInMonths() {
        return ChronoUnit.MONTHS.between(LocalDateTime.now(), maturityDate);
    }

    public double getInterestRate() {
        return interestRate;
    }
}
