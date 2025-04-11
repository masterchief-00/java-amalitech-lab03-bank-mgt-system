package com.kwizera.javaamalilabs03bankmgtsystem.session;


import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;

public class SessionManager {
    private static Account account;

    public static void setAccount(Account acc) {
        account = acc;
    }

    public static Account getAccount() {
        return account;
    }

    public static void clear() {
        account = null;
    }
}
