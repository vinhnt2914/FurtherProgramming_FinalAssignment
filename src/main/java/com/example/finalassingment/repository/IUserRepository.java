package com.example.finalassingment.repository;

import com.example.finalassingment.model.User;

public interface IUserRepository {
    User findUser(String username, String password);
    User findByID(int id);
    void removeByID(int id);
    void update(User user);
}
