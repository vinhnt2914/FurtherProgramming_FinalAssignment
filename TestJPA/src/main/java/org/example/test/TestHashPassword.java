package org.example.test;
/**
 * @author Group 11
 */
import org.example.model.User;
import org.example.repository.impl.UserRepository;

import java.util.List;

public class TestHashPassword {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        List<User> userList = repository.getAll();
        userList.forEach(System.out::println);
    }
}
