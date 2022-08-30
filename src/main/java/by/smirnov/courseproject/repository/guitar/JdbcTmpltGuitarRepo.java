package by.smirnov.courseproject.repository.guitar;

import by.smirnov.courseproject.model.Guitar;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTmpltGuitarRepo implements GuitarRepoInterface {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GuitarRowMapper guitarRowMapper;

    @Override
    public Guitar findById(Long id) {
        return jdbcTemplate.queryForObject("select * from guitarshop.guitars where id = " + id, guitarRowMapper);
    }

    @Override
    public Optional<Guitar> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Guitar> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<Guitar> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from guitarshop.guitars where is_deleted = false order by id limit "
                + limit + " offset " + offset, guitarRowMapper);
    }

    @Override
    public Guitar create(Guitar object) {
        final String insertQuery =
                "insert into guitarshop.guitars (typeof, shape, series, model, strings_qnt, neck, bridge, " +
                        "body_material, price, prod_country, brand_id) " +
                        " values (:typeOf, :shape, :series, :model, :stringsQnt, :neck, :bridge, \" +\n" +
                        "                        \":bodyMaterial, :price, :prodCountry, :brandId);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("typeOf", object.getTypeOf());
        mapSqlParameterSource.addValue("shape", object.getShape());
        mapSqlParameterSource.addValue("series", object.getSeries());
        mapSqlParameterSource.addValue("model", object.getModel());
        mapSqlParameterSource.addValue("stringsQnt", object.getStringsQnt());
        mapSqlParameterSource.addValue("neck", object.getNeck());
        mapSqlParameterSource.addValue("bridge", object.getBridge());
        mapSqlParameterSource.addValue("bodyMaterial", object.getBodyMaterial());
        mapSqlParameterSource.addValue("price", object.getPrice());
        mapSqlParameterSource.addValue("prodCountry", object.getProdCountry());
        mapSqlParameterSource.addValue("brandId", object.getBrandId());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        return null;
    }

    @Override
    public Guitar update(Guitar object) {
        final String updateQuery =
                "update guitarshop.users set first_name=:firstName, last_name=:lastName, user_role=:userRole, " +
                        "modification_date=current_timestamp where id=:id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("typeOf", object.getTypeOf());
        mapSqlParameterSource.addValue("shape", object.getShape());
        mapSqlParameterSource.addValue("series", object.getSeries());
        mapSqlParameterSource.addValue("model", object.getModel());
        mapSqlParameterSource.addValue("stringsQnt", object.getStringsQnt());
        mapSqlParameterSource.addValue("neck", object.getNeck());
        mapSqlParameterSource.addValue("bridge", object.getBridge());
        mapSqlParameterSource.addValue("bodyMaterial", object.getBodyMaterial());
        mapSqlParameterSource.addValue("price", object.getPrice());
        mapSqlParameterSource.addValue("prodCountry", object.getProdCountry());
        mapSqlParameterSource.addValue("brandId", object.getBrandId());

        namedParameterJdbcTemplate.update(updateQuery, mapSqlParameterSource);
        return null;
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("update guitarshop.guitars set is_deleted=true, termination_date=current_timestamp where id=?", id);
        return id;
    }

    @Override
    public Map<String, Object> showAverageGuitarPrice() {
        return jdbcTemplate.query("select guitarshop.get_guitars_stats_average_price(true)", resultSet -> {
            resultSet.next();
            return Collections.singletonMap("avg", resultSet.getDouble(1));
        });
    }
}
