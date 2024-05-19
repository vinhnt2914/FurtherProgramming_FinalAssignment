package com.example.finalassingment.model;
/**
 * @author Group 11
 */
public class Admin extends User {
    private final String username = "admin";
    private final String password = "123456";

    // Create a private static instance
    private static Admin instance = null;

    // Make the constructor private to prevent instantiation
    private Admin() {
    }

    // Provide a public static method to get the instance
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }
}

