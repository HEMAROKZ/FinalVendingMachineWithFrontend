package com.VendingMachine.VendingMachine01.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class InventoryDTO implements Serializable {
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final int productId;

    private final String name;
    @NotNull
    private final int productPrice;
    @NotNull
    private final int productInventoryCount;


    public InventoryDTO(int productId, String name, int productPrice, int productInventoryCount) {
        this.productId = productId;
        this.name = name;
        this.productPrice = productPrice;
        this.productInventoryCount = productInventoryCount;
    }

    public InventoryDTO(InventoryDTOBuilder inventoryDTOBuilder) {
        this.productId = inventoryDTOBuilder.productId;
        this.name = inventoryDTOBuilder.name;
        this.productPrice = inventoryDTOBuilder.productPrice;
        this.productInventoryCount = inventoryDTOBuilder.productInventoryCount;
    }

    public static InventoryDTOBuilder builder(){
        return new InventoryDTOBuilder();
    }
    public int getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }


    public int getProductPrice() {
        return productPrice;
    }


    public int getProductInventoryCount() {
        return productInventoryCount;
    }

    public static class InventoryDTOBuilder {
        private  int productId;
        private  String name;
        private  int productPrice;
        private  int productInventoryCount;

        public InventoryDTOBuilder() {
        }

        public InventoryDTOBuilder productId(int productId) {
            this.productId = productId;
            return this;
        }

        public InventoryDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InventoryDTOBuilder productPrice(int productPrice) {
            this.productPrice = productPrice;
            return this;
        }
        public InventoryDTOBuilder productInventoryCount(int productInventoryCount) {
            this.productInventoryCount = productInventoryCount;
            return this;
        }
        public InventoryDTO build() {
            return new InventoryDTO(this);
        }

    }
}
