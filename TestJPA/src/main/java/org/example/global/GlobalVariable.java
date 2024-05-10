package org.example.global;

import org.example.model.User;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.provider.InsuranceSurveyor;

public class GlobalVariable {
    private static Role role;
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
}
