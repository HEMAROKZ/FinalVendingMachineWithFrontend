package com.VendingMachine.VendingMachine01.model;

public class OrderLine {
    private int order_id;

    private int line_num;

    private String product_id;

    @Override
    public String toString() {
        return "OrderLine{" +
                "order_id=" + order_id +
                ", line_num=" + line_num +
                ", product_id='" + product_id + '\'' +
                '}';
    }

    public OrderLine() {
    }

    public OrderLine(int order_id, int line_num, String product_id) {
        this.order_id = order_id;
        this.line_num = line_num;
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getLine_num() {
        return line_num;
    }

    public void setLine_num(int line_num) {
        this.line_num = line_num;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
