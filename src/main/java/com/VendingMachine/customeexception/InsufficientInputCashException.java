package com.VendingMachine.customeexception;

public class InsufficientInputCashException extends RuntimeException {


    public InsufficientInputCashException(String message) {
        super(message);
    }

}
