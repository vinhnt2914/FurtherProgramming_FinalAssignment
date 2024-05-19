package org.example.model.customer;

/**
 * @author Group 11
 */
import org.example.model.User;

//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer extends User {
    public Customer(GenericCustomerBuilder builder) {
        super(builder);
    }

    public Customer() {
        super();
    }

    @Override
    public String toString() {

        return "Customer ID: " + id + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Address: " + address + "\n" +
                "Full Name: " + fullName + "\n" +
                "Claim List:\n";
    }

    public static abstract class GenericCustomerBuilder<T extends GenericCustomerBuilder<T>> extends GenericUserBuilder<T> {
    }
}
