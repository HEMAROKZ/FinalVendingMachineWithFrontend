package com.VendingMachine.VendingMachine01.customeexception;

public class NoExactChangeException extends RuntimeException{


    public NoExactChangeException(String message) {
        super(message);
    }

    public NoExactChangeException(String message, Throwable cause) {
        super(message, cause);
    }

}
