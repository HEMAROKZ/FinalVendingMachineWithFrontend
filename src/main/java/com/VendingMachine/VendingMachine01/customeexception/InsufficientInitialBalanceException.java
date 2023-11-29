package com.VendingMachine.VendingMachine01.customeexception;

public class InsufficientInitialBalanceException extends RuntimeException {


    public InsufficientInitialBalanceException(String message) {
        super(message);
    }

    public InsufficientInitialBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
