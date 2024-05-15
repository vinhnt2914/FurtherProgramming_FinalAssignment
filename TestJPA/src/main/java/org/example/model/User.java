package org.example.model;

import jakarta.persistence.*;
import org.example.model.customer.Customer;
import org.example.utility.PasswordUtil;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(nullable = false, unique = true)
    protected String username;
    @Column(nullable = false)
    protected String password;
    @Column
    protected String fullName;
    @Column
    protected String email;
    @Column
    protected String phone;
    @Column
    protected String address;

    public User(GenericUserBuilder builder) {
        this.username = builder.username;
        this.password = PasswordUtil.encrypt(builder.password);
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.fullName = builder.fullName;
    }

    public User() {
    }

    public static abstract class GenericUserBuilder<T extends GenericUserBuilder<T>> {
        protected String username;
        protected String password;
        protected String email;
        protected String phone;
        protected String address;
        protected String fullName;
        public T self() {
            return (T) this;
        }
        public T username(String username) {
            this.username = username;
            return self();
        }
        public T password(String password) {
            this.password = password;
            return self();
        }
        public T email(String email) {
            this.email = email;
            return self();
        }
        public T phone(String phone) {
            this.phone = phone;
            return self();
        }
        public T address(String address) {
            this.address = address;
            return self();
        }
        public T fullName(String fullName) {
            this.fullName = fullName;
            return self();
        }

        public abstract User build();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordUtil.encrypt(password); // Encrypt the password
    }

    public String getDecryptedPassword() {
        return PasswordUtil.decrypt(this.password); // Decrypt the password
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
