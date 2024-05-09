package org.example.global;

import org.example.model.User;

public class GlobalVariable {
    public static String role;
    public static User user;

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        GlobalVariable.role = role;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        GlobalVariable.user = user;
    }
}
