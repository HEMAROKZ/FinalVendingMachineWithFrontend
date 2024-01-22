package com.VendingMachine.VendingMachine01.util;

public class SqlQueries {

    // Inventory SQL queries as constants
    public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM productlist";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM productlist WHERE productId = :productId";
    public static final String INSERT_PRODUCT = "INSERT INTO productlist (productId, name, productInventoryCount, productPrice) VALUES (:productId, :name, :productInventoryCount, :productPrice)";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM productlist WHERE productId = ?";
    public static final String UPDATE_PRODUCT = "UPDATE productlist SET name = :name, productInventoryCount = :productInventoryCount, productPrice = :productPrice WHERE productId = :productId";

    // Initial balance DAO query
    public static final String SELECT_PURCHASE_HISTORY_BY_ID = "SELECT vendingMachineBalance FROM orders WHERE id = :id";

    public static final String SELECT_ALL_PURCHASE_HISTORY = "SELECT * FROM orders";

    public static final String INSERT_PURCHASE_HISTORY = "INSERT INTO orders (id,order_id, order_time,  customerInputAmount, balanceAmount, vendingMachineBalance)" +
            " VALUES (:id,:order_id ,:order_time, :customerInputAmount, :balanceAmount, :vendingMachineBalance)";
    public static final String INSERT_ORDER_LINE = "INSERT INTO order_line (order_id, line_num,  product_id)" +
            " VALUES (:order_id,:line_num ,:product_id)";


    /////////////////////
    public static final String SELECT_ALL_DENOMINATIONS = "SELECT * FROM denominations";
    public static final String UPDATE_DENOMINATION = "UPDATE denominations SET denominationType = :denominationType, count = :count WHERE id = :id";
    public static final String SELECT_DENOMINATION_BY_TYPE = "SELECT * FROM denominations WHERE denominationType = :denominationType";

    ////////////////////
    private SqlQueries() {
        // Private constructor to prevent instantiation
    }
}
