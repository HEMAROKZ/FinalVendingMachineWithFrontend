package com.VendingMachine.VendingMachine01.customeexception;

public class InsufficientInitialBalanceException extends RuntimeException {


    public InsufficientInitialBalanceException(String message) {
        super(message);
    }


}
