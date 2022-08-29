package by.smirnov.courseproject.repository.user;

import by.smirnov.courseproject.repository.CRUDRepository;
import by.smirnov.courseproject.model.User;

import java.util.Map;

public interface UserRepositoryInterface extends CRUDRepository<Long, User> {
    Map<String, Object> getUserStats();
}
