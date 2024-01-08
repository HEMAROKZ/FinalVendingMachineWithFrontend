package com.VendingMachine.VendingMachine01.util;

public class TransactionIdGenerator {

    private static int transactionCounter = 0;

    public synchronized static int generateTransactionId() {
        return ++transactionCounter;
    }
}
