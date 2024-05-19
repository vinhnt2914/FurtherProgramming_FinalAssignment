package com.example.finalassingment.repository;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.User;

public interface IUserRepository {
    User findUser(String username, String password);
    User findByID(int id);
    void update(User user);
}
