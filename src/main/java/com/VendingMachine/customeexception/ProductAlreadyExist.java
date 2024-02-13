package com.VendingMachine.customeexception;

public class ProductAlreadyExist  extends RuntimeException{

    public ProductAlreadyExist(String message) {
        super(message);
    }

}
