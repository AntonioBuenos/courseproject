package by.smirnov.courseproject.repository;

import by.smirnov.courseproject.repository.user.UserRepositoryInterface;
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
        return jdbcTemplate.queryForObject("select * from guitarshop.users where id = " + id, userRowMapper);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from guitarshop.users where is_deleted = false order by id limit "
                + limit + " offset " + offset, userRowMapper);
    }

    //зачем нам в CRUDRepository возврат этих значений? User? Если мы возвращаемые данные нигде не используем?
    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into guitarshop.users (first_name, last_name) " +
                        " values (:firstName, :lastName);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", object.getFirstName());
        mapSqlParameterSource.addValue("lastName", object.getLastName());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

/*        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('guitarshop.users_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });*/

        //return findById(lastInsertId);
        return null;
    }

    @Override
    public User update(User object) {
        final String updateQuery =
                "update guitarshop.users set first_name=:firstName, last_name=:lastName, user_role=:userRole, " +
                        "modification_date=current_timestamp where id=:id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", object.getFirstName());
        mapSqlParameterSource.addValue("lastName", object.getLastName());
        mapSqlParameterSource.addValue("userRole", object.getUserRole());
        mapSqlParameterSource.addValue("id", object.getId());

        namedParameterJdbcTemplate.update(updateQuery, mapSqlParameterSource);
        return null;
    }
//Зачем нам MapSqlParameterSource и NamedParameterJdbcTemplate,
// если jdbcTemplate тоже позволяет сделать Prepared Statement с "?"?
/*    @Override
    public User update(User object) {
        jdbcTemplate.update("UPDATE guitarshop.users SET first_name=?, last_name=?, user_role=? WHERE id=?",
                object.getFirstName(), object.getLastName(), object.getUserRole(), object.getId());
        return null;
    }*/

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("update guitarshop.users set is_deleted=true, termination_date=current_timestamp where id=?", id);
        return id;
    }

    @Override
    public Map<String, Object> getUserStats() {
        return null;
    }
}
