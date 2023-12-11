package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.util.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.VendingMachine.VendingMachine01.model.Inventry;

import java.util.List;

@Repository
public class InventoryDAOImp implements InventoryDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InventoryDAOImp(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public  NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public  List<Inventry> findAll() {
        return getNamedParameterJdbcTemplate().query(SqlQueries.SELECT_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Inventry.class));
    }

    @Override
    public  List<Inventry> findById(final int productId) {
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("productId", productId);
        return getNamedParameterJdbcTemplate().query(SqlQueries.SELECT_PRODUCT_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Inventry.class));
    }

    @Override
    public  List<Inventry> findByInventryCount(final int productInventoryCount) {
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("productInventoryCount", productInventoryCount);
        return getNamedParameterJdbcTemplate().query(SqlQueries.SELECT_PRODUCTS_BY_COUNT, mapSqlParameterSource , new BeanPropertyRowMapper<>(Inventry.class));
    }

    @Override
    public  int save(final InventoryDTO e) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("productId", e.getProductId());
        paramSource.addValue("name", e.getName());
        paramSource.addValue("productInventoryCount",e.getProductInventoryCount());
        paramSource.addValue("productPrice", e.getProductPrice());

        int update = getNamedParameterJdbcTemplate().update(SqlQueries.INSERT_PRODUCT, paramSource);
        if(update == 1) {
            System.out.println("product is added..");
        }
        return  update;
    }

    @Override
    public  int updatedStock(final int productId, final int productInventoryCount) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("productId", productId).addValue("productInventoryCount", productInventoryCount);
        return getNamedParameterJdbcTemplate().update("update productlist set productInventoryCount = :productInventoryCount where productId = :productId", sqlParameterSource);
    }

    @Override
    public  int deleteById(final int productId) {
        return jdbcTemplate.update(SqlQueries.DELETE_PRODUCT_BY_ID, productId);
    }

    @Override
    public  int update(final Inventry e) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("productId", e.getProductId())
                .addValue("name", e.getName())
                .addValue("productInventoryCount",e.getProductInventoryCount())
                .addValue("productPrice", e.getProductPrice());
        System.out.println("here in inventorydtoimp ========================");
        return  namedParameterJdbcTemplate.update(SqlQueries.UPDATE_PRODUCT, paramSource);
    }
}
