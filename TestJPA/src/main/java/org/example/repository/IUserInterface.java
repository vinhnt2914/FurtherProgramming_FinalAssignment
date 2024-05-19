package org.example.repository;
/**
 * @author Group 11
 */
import org.example.model.User;

public interface IUserInterface {
    User findUser(String username, String password);
    User findByID(int id);
    void update(User user);
}
