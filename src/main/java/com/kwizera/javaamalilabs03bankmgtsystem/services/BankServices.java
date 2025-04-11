package com.kwizera.javaamalilabs03bankmgtsystem.services;

import java.util.ArrayList;

public interface BankServices {
    boolean withdraw(double amount);

    void deposit(double amount);

    ArrayList<String> printHistory();

    ArrayList<String> printHistory(int N);

    double getBalance();
}
