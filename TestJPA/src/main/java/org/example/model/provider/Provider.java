package org.example.model.provider;

import jakarta.persistence.*;
import org.example.model.User;
import org.example.model.customer.Customer;
import org.example.utility.PasswordUtil;

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Provider extends User {

    public Provider(GenericProviderBuilder builder) {
        super(builder);
    }

    public Provider() {
    }

    public static abstract class GenericProviderBuilder<T extends GenericProviderBuilder<T>> extends GenericUserBuilder<T> {
        public abstract Provider build();
    }
}
