package com.VendingMachine.dao;

import com.VendingMachine.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.util.SqlQueries;
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
    public InitialBalanceAndPurchaseHistory getChange() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size());

        List<InitialBalanceAndPurchaseHistory> initialBalanceAndPurchaseHistoryList = namedParameterJdbcTemplate.query(
                SqlQueries.SELECT_PURCHASE_HISTORY_BY_ID,
                sqlParameterSource,
                new BeanPropertyRowMapper<>(InitialBalanceAndPurchaseHistory.class));
        if (!initialBalanceAndPurchaseHistoryList.isEmpty()) {
            return initialBalanceAndPurchaseHistoryList.get(0);
        } else {
            // Handle the case when the list is empty (no purchase history)
            throw new RuntimeException("No purchase history found.");
        }
    }

    @Override
    public  List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory() {
        return namedParameterJdbcTemplate.query(
                SqlQueries.SELECT_ALL_PURCHASE_HISTORY,
                new BeanPropertyRowMapper<>(InitialBalanceAndPurchaseHistory.class)
        );
    }

    @Override
    public void saveTransaction(final InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory ,final int customerInputAmount) {
        int initialBalance =( customerInputAmount + getChange().getVendingMachineBalance())-initialBalanceAndPurchaseHistory.getBalanceAmount();

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
                .addValue("order_id",initialBalanceAndPurchaseHistory.getOrder_id())
                .addValue("order_time", initialBalanceAndPurchaseHistory.getOrder_time())
                .addValue("customerInputAmount", initialBalanceAndPurchaseHistory.getCustomerInputAmount())
                .addValue("balanceAmount", initialBalanceAndPurchaseHistory.getBalanceAmount())
                .addValue("vendingMachineBalance", initialBalance);

        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT orders ON");
        namedParameterJdbcTemplate.update(SqlQueries.INSERT_PURCHASE_HISTORY, sqlParameterSource);
        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT orders OFF");
    }

}
