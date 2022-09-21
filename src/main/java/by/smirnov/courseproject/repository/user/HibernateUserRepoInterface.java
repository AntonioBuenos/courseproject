package by.smirnov.courseproject.repository.user;

import by.smirnov.courseproject.model.User;
import by.smirnov.courseproject.repository.CRUDRepository;

import java.util.List;
import java.util.Map;

public interface HibernateUserRepoInterface extends CRUDRepository<Long, User> {

    Map<String, Object> getUserStats();
    public List<User> showDeletedUsers();
}
