package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.util.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InitialBalanceDAOImp implements InitialBalanceDAO {
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InitialBalanceDAOImp(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public  InitialBalanceAndPurchaseHistory getChange() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size());

        List<InitialBalanceAndPurchaseHistory> initialBalanceAndPurchaseHistoryList = namedParameterJdbcTemplate.query(
                SqlQueries.SELECT_PURCHASE_HISTORY_BY_ID,
                sqlParameterSource,
                new BeanPropertyRowMapper<>(InitialBalanceAndPurchaseHistory.class)
        );
        return initialBalanceAndPurchaseHistoryList.get(0);
    }

    @Override
    public  List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory() {
        return namedParameterJdbcTemplate.query(
                SqlQueries.SELECT_ALL_PURCHASE_HISTORY,
                new BeanPropertyRowMapper<>(InitialBalanceAndPurchaseHistory.class)
        );
    }

    @Override
    public  int saveTransaction(final InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
                .addValue("productId", initialBalanceAndPurchaseHistory.getProductId())
                .addValue("product", initialBalanceAndPurchaseHistory.getProduct())
                .addValue("productPrice", initialBalanceAndPurchaseHistory.getProductPrice())
                .addValue("customerInputAmount", initialBalanceAndPurchaseHistory.getCustomerInputAmount())
                .addValue("vendingMachinebalance", initialBalanceAndPurchaseHistory.getVendingMachineBalance())
                .addValue("initialBalance", initialBalanceAndPurchaseHistory.getInitialBalance());

        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table ON");
        int rowsInserted = namedParameterJdbcTemplate.update(SqlQueries.INSERT_PURCHASE_HISTORY, sqlParameterSource);
        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table OFF");
        return rowsInserted;
    }

    @Override
    public  int initialBalanceUpdate(final int customerInputAmount) {
        String product = "update_balance";
        int initialBalance = customerInputAmount + getChange().getInitialBalance();

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
                .addValue("productId", 10001)
                .addValue("product", product)
                .addValue("productPrice", 0)
                .addValue("customerInputAmount", customerInputAmount)
                .addValue("vendingMachinebalance", 0)
                .addValue("initialBalance", initialBalance);

        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table ON");
        int rowsInserted = namedParameterJdbcTemplate.update(SqlQueries.INSERT_PURCHASE_HISTORY, sqlParameterSource);
        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table OFF");
        return rowsInserted;
    }
}
