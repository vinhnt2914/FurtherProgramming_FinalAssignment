package com.example.finalassingment.test;

import com.example.finalassingment.repository.impl.UserRepository;

public class TestLoginUserRetrieve {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        System.out.println(repository.findUser("vinhrmit1234", "Rmit@1234"));
        repository.close();
    }
}
