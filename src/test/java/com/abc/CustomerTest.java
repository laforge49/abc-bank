package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount)
                .openAccount(maxiAccount);

        checkingAccount.deposit(100.0, LocalDate.parse("2011-06-15"));
        savingsAccount.deposit(4000.0, LocalDate.parse("2011-06-15"));
        savingsAccount.withdraw(200.0, LocalDate.parse("2011-06-16"));
        maxiAccount.deposit(4000.0, LocalDate.parse("2011-06-15"));
        maxiAccount.withdraw(200.0, LocalDate.parse("2011-06-16"));
        try {
            checkingAccount.deposit(50.0, LocalDate.parse("2011-06-14"));
            fail("unordered transaction not caught");
        } catch (IllegalArgumentException e) {}
        try {
            checkingAccount.withdraw(500.0, LocalDate.parse("2011-06-18"));
            fail("excessive withdrawal not caught");
        } catch (IllegalArgumentException e) {}
        checkingAccount.withdraw(50.0, LocalDate.parse("2011-06-18"));

        assertEquals("Statement for Henry for 2011-06-17\n" +
                "\n" +
                "Checking Account\n" +
                "  2011-06-15 deposit $100.00\n" +
                "Interest Earned $0.00\n" +
                "Balance $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  2011-06-15 deposit $4,000.00\n" +
                "  2011-06-16 withdrawal $200.00\n" +
                "Interest Earned $0.04\n" +
                "Balance $3,800.04\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  2011-06-15 deposit $4,000.00\n" +
                "  2011-06-16 withdrawal $200.00\n" +
                "Interest Earned $0.56\n" +
                "Balance $3,800.56\n" +
                "\n" +
                "Total In All Accounts $7,700.60", henry.getStatement(LocalDate.parse("2011-06-17")));

    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
