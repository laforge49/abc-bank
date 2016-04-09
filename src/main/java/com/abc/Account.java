package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public boolean invalidDate(LocalDate date) {
        if (transactions.isEmpty())
            return false;
        Transaction t = transactions.get(transactions.size() - 1);
        return date.compareTo(t.getTransactionDate()) < 0;
    }

    public void deposit(double amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (invalidDate(date)) {
            throw new IllegalArgumentException("date can not be less then previous transaction date");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    public void withdraw(double amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (invalidDate(date)) {
            throw new IllegalArgumentException("date can not be less then previous transaction date");
        } else if (amount > balance(date)) {
            throw new IllegalArgumentException("withdrawal amount exceeds current balance");
        } else {
            transactions.add(new Transaction(-amount, date));
        }
    }

    public double dailyEarnings(double balance, LocalDate date, int withdrawalCountDown) {
        int lengthOfYear = date.lengthOfYear();
        switch (accountType) {
            case SAVINGS:
                if (balance <= 1000)
                    return balance * 0.001 / lengthOfYear;
                else
                    return (1 + (balance - 1000) * 0.002) / lengthOfYear;
            case MAXI_SAVINGS:
                if (withdrawalCountDown > 0) {
                    return balance * 0.001 / lengthOfYear;
                }
                return balance * 0.05 / lengthOfYear;
            default:
                return balance * 0.001 / lengthOfYear;
        }
    }

    public double balance(LocalDate date) {
        if (transactions.isEmpty())
            return 0.0;
        double amount = 0.0;
        int pos = 0;
        LocalDate currentDate = transactions.get(0).getTransactionDate();
        int withdrawalCountDown = 0;
        while (currentDate.compareTo(date) <= 0) {
            amount += dailyEarnings(amount, currentDate, withdrawalCountDown);
            while (pos < transactions.size() &&
                    currentDate.compareTo(transactions.get(pos).getTransactionDate()) == 0) {
                double transactionAmount = transactions.get(pos).amount;
                if (transactionAmount < 0) {
                    withdrawalCountDown = 10;
                }
                amount += transactionAmount;
                pos += 1;
            }
            currentDate = currentDate.plusDays(1);
            if (withdrawalCountDown > 0) {
                withdrawalCountDown -= 1;
            }
        }
        return amount;
    }

    public double sumTransactions(LocalDate date) {
        double amount = 0.0;
        for (Transaction t : transactions)
            if (date.compareTo(t.getTransactionDate()) >= 0) {
                amount += t.amount;
            }
        return amount;
    }

    public double interestEarned(LocalDate date) {
        return balance(date) - sumTransactions(date);
    }

    public int getAccountType() {
        return accountType;
    }

}
