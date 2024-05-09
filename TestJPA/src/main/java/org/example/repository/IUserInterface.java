package org.example.repository;

import org.example.model.User;

public interface IUserInterface {
    User findUser(String username, String password);
    User getUserByID(int id);
}
