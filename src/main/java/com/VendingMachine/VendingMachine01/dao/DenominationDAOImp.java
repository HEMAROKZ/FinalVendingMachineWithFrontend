package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.Denomination;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.util.SqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public class DenominationDAOImp implements DenominationDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }


    @Override
    public Optional<Denomination> findById(int indexId) {
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("indexId", indexId);
        List<Denomination> result = getNamedParameterJdbcTemplate().query(
                SqlQueries.SELECT_DENOMINATION_BY_ID,
                mapSqlParameterSource,
                new BeanPropertyRowMapper<>(Denomination.class)
        );

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
    @Override
    public void update(Denomination denomination) {
//        String sql = "UPDATE Denomination SET " +
//                "fiftyRupee = :fiftyRupee, " +
//                "twentyRupee = :twentyRupee, " +
//                "tenRupee = :tenRupee, " +
//                "fiveRupee = :fiveRupee, " +
//                "twoRupee = :twoRupee, " +
//                "oneRupee = :oneRupee " +
//                "WHERE indexId = :indexId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("indexId", denomination.getIndexId());
        parameters.addValue("fiftyRupee", denomination.getFiftyRupee());
        parameters.addValue("twentyRupee", denomination.getTwentyRupee());
        parameters.addValue("tenRupee", denomination.getTenRupee());
        parameters.addValue("fiveRupee", denomination.getFiveRupee());
        parameters.addValue("twoRupee", denomination.getTwoRupee());
        parameters.addValue("oneRupee", denomination.getOneRupee());

        getNamedParameterJdbcTemplate().update(SqlQueries.UPDATE_DENOMINATION, parameters);
    }

}
