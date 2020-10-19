package spring.intro.dao;

import java.util.List;
import spring.intro.model.User;

public interface UserDao {
    void add(User user);

    List<User> listUsers();
}
