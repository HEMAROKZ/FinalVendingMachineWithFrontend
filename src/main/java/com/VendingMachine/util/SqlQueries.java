package com.VendingMachine.util;

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
    // Insert aggregated rows
    public static final String INSERT_ORDER_LINE_MERGED = "INSERT INTO order_line_merged (order_id, merged_line_no, merged_product_ids) " +
            "SELECT " +
            "    order_id, " +
            "    MAX(line_num) AS merged_line_no, " +
            "    STRING_AGG(CAST(product_id AS NVARCHAR(MAX)), ', ') AS merged_product_ids " +
            "FROM " +
            "    order_line " +
            "GROUP BY " +
            "    order_id;";
    public static final String DELETE_DUPLICATE = "WITH CTE AS (" +
            "   SELECT " +
            "       order_id, " +
            "       ROW_NUMBER() OVER (PARTITION BY order_id ORDER BY (SELECT NULL)) AS RowNum " +
            "   FROM " +
            "       order_line_merged" +
            ")" +
            "DELETE FROM CTE WHERE RowNum > 1;";
    public static final  String DELETE_ZERO_ORDER_LINE = "DELETE FROM order_line WHERE order_id = 0;";
    public static final String DELETE_ZERO_ORDER_MERGE = "DELETE FROM order_line_merged WHERE order_id = 0;";
    public static final String CREATE_ORDERLINE_MERGE = "IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'order_line_merged') " +
            "BEGIN " +
            "    CREATE TABLE [flywaydemo].[dbo].[order_line_merged] (" +
            "        order_id INT," +
            "        merged_line_no INT," +
            "        merged_product_ids NVARCHAR(MAX)" +
            "    );" +
            "END;";

    private SqlQueries() {
        // Private constructor to prevent instantiation
    }
}
