package by.smirnov.courseproject.dao;

import by.smirnov.courseproject.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.smirnov.courseproject.dao.user.UserTableColumns.*;

public class UserRowMapper implements RowMapper<User> {

    //Logger to be added
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();

        user.setId(rs.getLong(ID));
        user.setFirstName(rs.getString(NAME));
        user.setLastName(rs.getString(SURNAME));
        user.setUserRole(rs.getString(ROLE));
        user.setCreationDate(rs.getTimestamp(CREATED));
        user.setModificationDate(rs.getTimestamp(CHANGED));
        user.setDeleted(rs.getBoolean(IS_DELETED));
        user.setTerminationDate(rs.getTimestamp(TERMINATED));

        return user;
    }
}
