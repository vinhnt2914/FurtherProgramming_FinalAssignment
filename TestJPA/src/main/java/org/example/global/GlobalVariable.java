package org.example.global;

import org.example.model.User;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.provider.InsuranceSurveyor;

import java.net.URL;


public class GlobalVariable {
    private static Role role;
    private static User user;
    private static int userID;

    public static Role getRole() {
        return role;
    }

    public static void setRole(User user) {
        switch (user) {
            case Dependant dependant -> role = Role.Dependant;
            case PolicyOwner policyOwner -> role = Role.PolicyOwner;
            case PolicyHolder policyHolder -> role = Role.PolicyHolder;
            case InsuranceSurveyor insuranceSurveyor -> role = Role.Surveyor;
            case null, default -> role = Role.Manager;
        }
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        GlobalVariable.userID = userID;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        GlobalVariable.user = user;
    }

    public static void setRole(Role role) {
        GlobalVariable.role = role;
    }
    public static void setCurrentUser(User user) {
        GlobalVariable.user = user;
        setRole(user);
    }
}
