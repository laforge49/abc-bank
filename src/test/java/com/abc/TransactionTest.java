package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, LocalDate.parse("2011-06-15"));
        assertTrue(t instanceof Transaction);
    }
}
