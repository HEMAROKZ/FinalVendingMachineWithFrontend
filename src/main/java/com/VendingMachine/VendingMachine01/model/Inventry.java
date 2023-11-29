package com.VendingMachine.VendingMachine01.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table
public  class Inventry implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private   int productId;
    @NotBlank
    @Column
    private  String name;
    @NotNull
    @Column
    private  int productPrice;
    @NotNull
    @Column
    private  int productInventoryCount;

    @Override
    public String toString() {
        return "Inventry{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", productPrice=" + productPrice +
                ", productInventoryCount=" + productInventoryCount +
                '}';
    }

    public Inventry(int productId, String name, int productPrice, int productInventoryCount) {
        this.productId = productId;
        this.name = name;
        this.productPrice = productPrice;
        this.productInventoryCount = productInventoryCount;
    }


    public Inventry() {
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
