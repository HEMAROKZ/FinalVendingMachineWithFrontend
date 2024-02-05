package com.VendingMachine.customeexception;

public class NoExactChangeException extends RuntimeException{


    public NoExactChangeException(String message) {
        super(message);
    }


}
