package com.example.finalassingment.test;

import com.example.finalassingment.model.User;
import com.example.finalassingment.repository.impl.UserRepository;

import java.util.List;

public class TestHashPassword {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        List<User> userList = repository.getAll();
        userList.forEach(System.out::println);
    }
}
