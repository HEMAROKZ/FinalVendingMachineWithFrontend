package com.VendingMachine.VendingMachine01.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public  class Inventry  {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
                ", name='" + name + '\'' +
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

























// @JsonCreator
//    public Inventry() {
//        this.productId = 0; // You can set a default value here
//        this.name = null;   // You can set default values or choose to make these nullable
//        this.productPrice = 0;
//        this.productInventoryCount = 0;
//    }
//
//public Inventry(InventoryBuilder inventoryBuilder) {
//    this.productId = inventoryBuilder.productId;
//    this.name = inventoryBuilder.name;
//    this.productPrice = inventoryBuilder.productPrice;
//    this.productInventoryCount = inventoryBuilder.productInventoryCount;
//}
//
//
//    public static InventoryBuilder builder(){
//        return new InventoryBuilder();
//    }

//    public static class InventoryBuilder {
//        private int productId;
//
//        private String name;
//        private int productPrice;
//         private int productInventoryCount;
//
//
//        public InventoryBuilder() {
//        }
//
//        public InventoryBuilder withProductId(int productId) {
//            this.productId = productId;
//            return this;
//        }
//
//
//        public InventoryBuilder     withName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public InventoryBuilder withProductPrice(int productPrice) {
//            this.productPrice = productPrice;
//            return this;
//        }
//        public InventoryBuilder withProductInventoryCount(int productInventoryCount) {
//            this.productInventoryCount = productInventoryCount;
//            return this;
//        }
//
//        public Inventry build() {
//            return new Inventry(this);
//        }
//
//    }