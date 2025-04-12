package com.kwizera.javaamalilabs03bankmgtsystem.models;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// a simple transaction history node, with a prev and next pointer
public class Transaction {
    String type;
    double amount;
    LocalDateTime timestamp;
    Transaction next;
    Transaction prev;

    Transaction(String type, double amount, LocalDateTime timestamp, Transaction next, Transaction prev) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.next = next;
        this.prev = prev;
    }

    // overrides the built in toString method to customize a transaction to a human friendly format
    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        return timestamp.format(dateFormatter) + " - " + type + " -> " + formatter.format(amount) + " RWF";
    }
}
