package com.abc;

import java.time.LocalDate;

public class Transaction {
    public final double amount;

    private LocalDate transactionDate;

    public Transaction(double amount, LocalDate date) {
        this.amount = amount;
        this.transactionDate = date;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

}
