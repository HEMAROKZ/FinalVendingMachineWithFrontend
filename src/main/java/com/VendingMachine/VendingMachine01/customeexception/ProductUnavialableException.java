package com.VendingMachine.VendingMachine01.customeexception;

public class ProductUnavialableException extends RuntimeException{

    public ProductUnavialableException(String message) {
        super(message);
    }
}
