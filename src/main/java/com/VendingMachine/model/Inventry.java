package com.VendingMachine.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public  class Inventry  {

    private     int productId;

    private   String name;
    private  int productPrice;

    private  int productInventoryCount;
//
    public Inventry(int productId, String name, int productPrice, int productInventoryCount) {
        this.productId = productId;
        this.name = name;
        this.productPrice = productPrice;
        this.productInventoryCount = productInventoryCount;
    }

    public Inventry() {
    }

    @Override
    public String toString() {
        return "Inventry{" +
                "productId=" + productId +
                ", name='" + name +
                ", productPrice=" + productPrice +
                ", productInventoryCount=" + productInventoryCount +
                '}';
    }



    public int getProductId() {
        return productId;
    }


    public  String getName() {
        return name;
    }

    public  int getProductPrice() {
        return productPrice;
    }


    public  int getProductInventoryCount() {
        return productInventoryCount;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductInventoryCount(int productInventoryCount) {
        this.productInventoryCount = productInventoryCount;
    }

}

