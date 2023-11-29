package com.VendingMachine.VendingMachine01.customeexception;

public class ProductUnavialableException extends RuntimeException{

    public ProductUnavialableException(String message) {
        super(message);
    }
//throwable is super class of all exceptions
    public ProductUnavialableException(String message, Throwable cause) {
        super(message, cause);
    }
}
