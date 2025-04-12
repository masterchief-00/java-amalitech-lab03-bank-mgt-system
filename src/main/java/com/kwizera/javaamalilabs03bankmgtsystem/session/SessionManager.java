package com.kwizera.javaamalilabs03bankmgtsystem.session;


import com.kwizera.javaamalilabs03bankmgtsystem.models.Account;

// session class to manage the data of the current user logged in
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
