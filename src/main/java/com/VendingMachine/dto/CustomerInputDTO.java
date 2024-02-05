package com.VendingMachine.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CustomerInputDTO {
    @NotNull
    @JsonIgnore
    private final int productId;

    @NotNull
    @JsonIgnore
    private final int countOfProduct;

    @NotNull
    @JsonIgnore
    private final int price;


    @JsonCreator
    public static CustomerInputDTO create(@JsonProperty("product_code") int productId, @JsonProperty("giving_amount") int price) {
        return builder().withProductId(productId).withPrice(price).build();
    }




    public int getCountOfProduct() {
        return countOfProduct;
    }



    public int getProductId() {
        return productId;
    }


    public int getPrice() {
        return price;
    }

    public CustomerInputDTO(CustomerInputBuilder customerInputBuilder) {
        this.productId = customerInputBuilder.productId;
        this.price = customerInputBuilder.price;
        this.countOfProduct = customerInputBuilder.countOfProduct;
    }
    public static CustomerInputBuilder builder(){
        return new CustomerInputBuilder();
    }


    public static class CustomerInputBuilder {
        private int productId;
        private int countOfProduct;
        private int price;


        public CustomerInputBuilder() {
        }

        public CustomerInputBuilder withProductId(int productId) {
            this.productId = productId;
            return this;
        }

        public CustomerInputBuilder withPrice(int price) {
            this.price = price;
            return this;
        }
        public CustomerInputBuilder withCountOfProduct(int countOfProduct) {
            this.countOfProduct = countOfProduct;
            return this;
        }

        public CustomerInputDTO build() {
            return new CustomerInputDTO(this);
        }

    }
}