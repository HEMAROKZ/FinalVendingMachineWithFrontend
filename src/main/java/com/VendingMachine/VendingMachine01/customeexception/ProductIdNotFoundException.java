package com.VendingMachine.VendingMachine01.customeexception;

public class ProductIdNotFoundException extends RuntimeException{

    public ProductIdNotFoundException(String message) {
        super(message);
    }
//throwable is super class of all exceptions
    public ProductIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
