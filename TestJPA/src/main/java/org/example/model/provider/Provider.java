package org.example.model.provider;

import jakarta.persistence.*;
import org.example.model.User;
import org.example.model.customer.Customer;
import org.example.utility.PasswordUtil;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Provider extends User {

    public Provider(GenericProviderBuilder builder) {
        super(builder);
//        this.username = builder.username;
//        this.password = PasswordUtil.encrypt(builder.password);
//        this.email = builder.email;
//        this.phone = builder.phone;
//        this.address = builder.address;
//        this.fullName = builder.fullName;
    }

    public Provider() {
    }

    public static abstract class GenericProviderBuilder<T extends GenericProviderBuilder<T>> extends GenericUserBuilder<T> {
//        protected String username;
//        protected String password;
//        protected String email;
//        protected String phone;
//        protected String address;
//        protected String fullName;
//        public T self() {
//            return (T) this;
//        }
//        public T username(String username) {
//            this.username = username;
//            return self();
//        }
//        public T password(String password) {
//            this.password = password;
//            return self();
//        }
//        public T email(String email) {
//            this.email = email;
//            return self();
//        }
//        public T phone(String phone) {
//            this.phone = phone;
//            return self();
//        }
//        public T address(String address) {
//            this.address = address;
//            return self();
//        }
//        public T fullName(String fullName) {
//            this.fullName = fullName;
//            return self();
//        }
//
//        public abstract Provider build();
    }
}
