package com.VendingMachine.VendingMachine01.customeexception;

public class InsufficientInputCashException extends RuntimeException {


    public InsufficientInputCashException(String message) {
        super(message);
    }

    public InsufficientInputCashException(String message, Throwable cause) {
        super(message, cause);
    }
}
