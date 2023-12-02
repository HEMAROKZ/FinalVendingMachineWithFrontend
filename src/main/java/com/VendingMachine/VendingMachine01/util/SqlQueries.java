package com.VendingMachine.VendingMachine01.util;

public class SqlQueries {

    // Inventory SQL queries as constants
    public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM productlist";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM productlist WHERE productId = :productId";
    public static final String SELECT_PRODUCTS_BY_COUNT = "SELECT * FROM productlist WHERE productInventoryCount > :productInventoryCount";
    public static final String INSERT_PRODUCT = "INSERT INTO productlist (productId, name, productInventoryCount, productPrice) VALUES (:productId, :name, :productInventoryCount, :productPrice)";
    public static final String UPDATE_STOCK = "UPDATE productlist SET productInventoryCount = :productInventoryCount WHERE productId = :productId";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM productlist WHERE productId = ?";
    public static final String UPDATE_PRODUCT = "UPDATE productlist SET name = :name, productInventoryCount = :productInventoryCount, productPrice = :productPrice WHERE productId = :productId";

    // Initial balance DAO query
    public static final String SELECT_PURCHASE_HISTORY_BY_ID =
            "SELECT initialBalance FROM purchasehistory_table WHERE id = :id";

    public static final String SELECT_ALL_PURCHASE_HISTORY =
            "SELECT * FROM purchasehistory_table";

    public static final String INSERT_PURCHASE_HISTORY =
            "INSERT INTO purchasehistory_table (id, productId, product, productPrice, customerInputAmount, vendingMachinebalance, initialBalance)" +
                    " VALUES (:id, :productId, :product, :productPrice, :customerInputAmount, :vendingMachinebalance, :initialBalance)";

// denomination DAO query
public static final String SELECT_DENOMINATION_BY_ID =
        "SELECT * FROM Denomination WHERE indexId = :indexId";

    public static final String UPDATE_DENOMINATION =
            "UPDATE Denomination SET " +
                    "fiftyRupee = :fiftyRupee, " +
                    "twentyRupee = :twentyRupee, " +
                    "tenRupee = :tenRupee, " +
                    "fiveRupee = :fiveRupee, " +
                    "twoRupee = :twoRupee, " +
                    "oneRupee = :oneRupee " +
                    "WHERE indexId = :indexId";
    private SqlQueries() {
        // Private constructor to prevent instantiation
    }
}
