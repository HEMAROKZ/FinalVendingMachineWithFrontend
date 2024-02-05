package com.VendingMachine.util;

public class TransactionIdGenerator {

    private static int transactionCounter = 0;

    public synchronized static int generateTransactionId() {
        return ++transactionCounter;
    }
}
