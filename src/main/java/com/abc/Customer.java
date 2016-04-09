package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned(LocalDate date) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(date);
        return total;
    }

    public String getStatement(LocalDate date) {
        String statement = null;
        statement = "Statement for " + name + " for " + date + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a, date) + "\n";
            total += a.balance(date);
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a, LocalDate date) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            if (date.compareTo(t.getTransactionDate()) >= 0) {
                s += "  " + t.getTransactionDate() +
                        " " + (t.amount < 0 ? "withdrawal" : "deposit") +
                        " " + toDollars(t.amount) + "\n";
                total += t.amount;
            }
        }
        double interestEarned = a.interestEarned(date);
        s += "Interest Earned " + toDollars(interestEarned) + "\n";
        s += "Balance " + toDollars(total + interestEarned);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
