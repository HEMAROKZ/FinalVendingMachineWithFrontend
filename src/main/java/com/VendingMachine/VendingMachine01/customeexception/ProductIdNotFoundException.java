package com.VendingMachine.VendingMachine01.customeexception;

public class ProductIdNotFoundException extends RuntimeException{

    public ProductIdNotFoundException(String message) {
        super(message);
    }

}
