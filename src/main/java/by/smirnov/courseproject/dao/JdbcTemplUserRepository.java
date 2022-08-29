package by.smirnov.courseproject.dao;

import by.smirnov.courseproject.dao.user.UserRepositoryInterface;
import by.smirnov.courseproject.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTemplUserRepository implements UserRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("select * from guitarshop.users where id = " + id
                + " and is_deleted = false", userRowMapper);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return null;
    }

    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into guitarshop.users (first_name, last_name) " +
                        " values (:firstName, :lastName);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("first_name", object.getFirstName());
        mapSqlParameterSource.addValue("last_name", object.getLastName());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('carshop.users_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public User update(User object) {
        jdbcTemplate.update("UPDATE guitarshop.users SET first_name=?, last_name=?, user_role=? WHERE id=?",
                object.getFirstName(), object.getLastName(), object.getUserRole(), object.getId());
        return object;
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("UPDATE guitarshop.users SET is_deleted=true WHERE id=?", id);
        return id;
    }

    @Override
    public Map<String, Object> getUserStats() {
        return null;
    }
}
