package com.VendingMachine.dao;

import com.VendingMachine.dto.controllerDTO.DenominationType;
import com.VendingMachine.model.Denomination;
import com.VendingMachine.util.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DenominationDAOImp implements DenominationDAO {

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DenominationDAOImp(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public  NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
    @Override
    public List<Denomination> getAllDenominations() {
        return namedParameterJdbcTemplate.query(
                SqlQueries.SELECT_ALL_DENOMINATIONS,
                new BeanPropertyRowMapper<>(Denomination.class)
        );
    }

    @Override
    public Denomination getDenominationByDenominationType(DenominationType denominationType) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("denominationType", denominationType.toString());

        List<Denomination> denominations = namedParameterJdbcTemplate.query(
                SqlQueries.SELECT_DENOMINATION_BY_TYPE,
                sqlParameterSource,
                new BeanPropertyRowMapper<>(Denomination.class)
        );

        if (!denominations.isEmpty()) {
            return denominations.get(0);
        } else {
            throw new RuntimeException("Denomination with type " + denominationType + " not found.");
        }
    }

    @Override
    public void updateDenomination(Denomination denomination) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", denomination.getId())
                .addValue("denominationType", denomination.getDenominationType().toString())
                .addValue("count", denomination.getCount());

        namedParameterJdbcTemplate.update(SqlQueries.UPDATE_DENOMINATION, sqlParameterSource);
    }


}

