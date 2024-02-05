package com.VendingMachine.customeexception;

public class ProductUnavialableException extends RuntimeException{

    public ProductUnavialableException(String message) {
        super(message);
    }
}
