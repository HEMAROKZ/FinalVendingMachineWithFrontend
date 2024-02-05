package com.VendingMachine.customeexception;

public class InsufficientInitialBalanceException extends RuntimeException {


    public InsufficientInitialBalanceException(String message) {
        super(message);
    }


}
