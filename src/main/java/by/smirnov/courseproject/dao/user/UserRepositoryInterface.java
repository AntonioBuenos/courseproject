package by.smirnov.courseproject.dao.user;

import by.smirnov.courseproject.dao.CRUDRepository;
import by.smirnov.courseproject.model.User;

import java.util.Map;

public interface UserRepositoryInterface extends CRUDRepository<Long, User> {
    Map<String, Object> getUserStats();
}
