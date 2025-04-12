package com.kwizera.javaamalilabs03bankmgtsystem.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FixedDepositAccount extends Account {
    private final LocalDateTime maturityDate;
    private final double interestRate;

    // uses constructor chaining to create an instance of a fixed deposit account with extra attributes
    public FixedDepositAccount(String accountHolder, String accountNumber, double balance, LocalDateTime maturityDate, double interestRate) {
        super(accountHolder, accountNumber, balance, "fixed_deposit");
        this.maturityDate = maturityDate;
        this.interestRate = interestRate;
    }

    // overriding the abstract withdraw method enforcing fixed deposit account rules
    @Override
    public boolean withdraw(double amount) {

        // restricting withdraws and adding interest for only mature accounts
        if (isMature() && amount <= balance) {
            makeMature();
            balance = 0;
            recordTransaction("withdraw", amount);
            return true;
        }
        return false;
    }

    // overriding deposit to enforce fixed deposits only
    @Override
    public void deposit(double amount) {
        if (isMature()) {
            balance += amount;
            recordTransaction("deposit", amount);
        }
    }

    // method to apply interest amounts
    public void makeMature() {
        double interestAmount = calculateInterest();
        balance += interestAmount;
    }

    // method to calculate interest respective to the maturity period
    public double calculateInterest() {
        long months = Math.abs(ChronoUnit.MONTHS.between(maturityDate, LocalDateTime.now()));
        return ((balance * interestRate * months) / 100);
    }

    // determining if the account is mature for deposits
    public boolean isMature() {
        return LocalDateTime.now().isAfter(maturityDate);
    }

    // calculating how long before maturity date(in months)
    public long maturesInMonths() {
        return ChronoUnit.MONTHS.between(LocalDateTime.now(), maturityDate);
    }

    public double getInterestRate() {
        return interestRate;
    }
}
