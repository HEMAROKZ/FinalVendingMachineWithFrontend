package com.VendingMachine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;



public class PurchaseInputDTO {

    @NotNull
    @JsonIgnore
    private final String name;

        @NotNull
        @JsonIgnore
        private final int productId;

        @NotNull
        @JsonIgnore
        private final int countOfProduct;

        @NotNull
        @JsonIgnore
        private final int price;

        @NotNull
        @JsonIgnore
        private final int quantity;


        public PurchaseInputDTO(PurchaseInputBuilder purchaseInputBuilder) {
            this.productId = purchaseInputBuilder.productId;
            this.price = purchaseInputBuilder.price;
            this.countOfProduct = purchaseInputBuilder.countOfProduct;
            this.quantity = purchaseInputBuilder.quantity;
            this.name = purchaseInputBuilder.name;
        }

    public static PurchaseInputBuilder builder(){
        return new PurchaseInputBuilder();
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
    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

        public static class PurchaseInputBuilder {
            private int productId;
            private int countOfProduct;
            private int price;

            private int quantity;

            private String name;



            public PurchaseInputBuilder() {
            }

            public PurchaseInputBuilder withProductId(int productId) {
                this.productId = productId;
                return this;
            }
            public PurchaseInputBuilder withName(String name) {
                this.name = name;
                return this;
            }

            public PurchaseInputBuilder withQuantity(int quantity) {
                this.quantity = quantity;
                return this;
            }
            public PurchaseInputBuilder withPrice(int price) {
                this.price = price;
                return this;
            }
            public PurchaseInputBuilder withCountOfProduct(int countOfProduct) {
                this.countOfProduct = countOfProduct;
                return this;
            }

            public PurchaseInputDTO build() {
                return new PurchaseInputDTO(this);
            }

        }

    @Override
    public String toString() {
        return "PurchaseInputDTO{" +
                "name='" + name + '\'' +
                ", productId=" + productId +
                ", countOfProduct=" + countOfProduct +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
