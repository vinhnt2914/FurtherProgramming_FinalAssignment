package org.example.repository;

import org.example.model.User;
import org.example.repository.impl.UserRepository;
import org.example.utility.PasswordUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void getAll() {
        UserRepository repository = new UserRepository();
        List<User> userList = repository.getAll();
        assertNotNull(userList);
        repository.close();
    }

    @Test
    void findUser() {
        UserRepository repository = new UserRepository();
        String username = "holder21rmit1234";
        String password = PasswordUtil.encrypt("Rmit@1234");
        User foundUser = repository.findUser(username, password);
        assertNotNull(foundUser);
        repository.close();
    }

    @Test
    void findByID() {
        UserRepository repository = new UserRepository();
        assertNotNull(repository.findByID(1));
        repository.close();
    }
}