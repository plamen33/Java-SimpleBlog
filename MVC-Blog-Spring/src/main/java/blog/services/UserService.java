package blog.services;

import blog.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User create(User user);
    User edit(User user);
    void deleteById(Long id);
    boolean authenticate(String username, String password) throws SQLException;
    User login(String username, String password);
    User register(String username, String password, String fullName);
    void setPassword(String username, String newPassword);
}
