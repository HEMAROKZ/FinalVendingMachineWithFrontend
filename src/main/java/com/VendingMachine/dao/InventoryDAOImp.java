package com.VendingMachine.dao;

import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.OrderLine;
import com.VendingMachine.model.Inventry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.VendingMachine.util.SqlQueries.*;

@Repository
public class InventoryDAOImp implements InventoryDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(InventoryDAOImp.class);

    public InventoryDAOImp(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public  NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public  List<Inventry> findAll() {
        return getNamedParameterJdbcTemplate().query(SELECT_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Inventry.class));
    }

    @Override
    public  List<Inventry> findById(final int productId) {
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("productId", productId);
        return getNamedParameterJdbcTemplate().query(SELECT_PRODUCT_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Inventry.class));
    }



    @Override
    public  int save(final InventoryDTO e) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("productId", e.getProductId());
        paramSource.addValue("name", e.getName());
        paramSource.addValue("productInventoryCount",e.getProductInventoryCount());
        paramSource.addValue("productPrice", e.getProductPrice());

        int update = getNamedParameterJdbcTemplate().update(INSERT_PRODUCT, paramSource);
        if(update == 1) {
            log.info("successful update");
        }
        return  update;
    }

    @Override
    public void updatedStock(final int productId, final int productInventoryCount) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("productId", productId).addValue("productInventoryCount", productInventoryCount);
        getNamedParameterJdbcTemplate().update("update productlist set productInventoryCount = :productInventoryCount where productId = :productId", sqlParameterSource);
    }

    @Override
    public int deleteById(final int productId) {
        return jdbcTemplate.update(DELETE_PRODUCT_BY_ID, productId);
    }

    @Override
    public  int update(final Inventry e) {
        log.warn("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+e);
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("productId", e.getProductId())
                .addValue("name", e.getName())
                .addValue("productInventoryCount",e.getProductInventoryCount())
                .addValue("productPrice", e.getProductPrice());
        return  namedParameterJdbcTemplate.update(UPDATE_PRODUCT, paramSource);
    }

    @Override
    public int save_orderDetails(final OrderLine orderLine) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("order_id", orderLine.getOrder_id());
        paramSource.addValue("line_num", orderLine.getLine_num());
        paramSource.addValue("product_id", orderLine.getProduct_id());

        // Step 1: Save the order line details
        int update = getNamedParameterJdbcTemplate().update(INSERT_ORDER_LINE, paramSource);

        if (update == 1) {
            log.info("Successful update");
        }

        return update;
    }
@Override
    public void createOrderLineMergedTable() {

        getNamedParameterJdbcTemplate().getJdbcTemplate().execute(CREATE_ORDERLINE_MERGE);
    }
    @Override
    public void mergeOrderLineRows() {
        getNamedParameterJdbcTemplate().getJdbcTemplate().execute(INSERT_ORDER_LINE_MERGED);
        getNamedParameterJdbcTemplate().getJdbcTemplate().execute(DELETE_DUPLICATE);

}


    @Override
    public void deleteRowsWithOrderIdZero() {

        getNamedParameterJdbcTemplate().getJdbcTemplate().execute(DELETE_ZERO_ORDER_LINE);
       // getNamedParameterJdbcTemplate().getJdbcTemplate().execute(DELETE_ZERO_ORDER);
        getNamedParameterJdbcTemplate().getJdbcTemplate().execute(DELETE_ZERO_ORDER_MERGE);

    }

}
