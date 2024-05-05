package org.example.model.customer;

import jakarta.persistence.*;
import org.example.model.User;
import org.example.model.items.Claim;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column( nullable = false)
    private String password;
    private String email;
    private String phone;
    private String address;
    private String fullName;
    @OneToMany(mappedBy = "insuredPerson",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch=FetchType.LAZY)
    private Set<Claim> claimList;

    public Customer(GenericCustomerBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.fullName = builder.fullName;
        this.claimList = new HashSet<>();
    }

    public Customer() {
        super();
    }

//    public Customer(GenericCustomerBuilder builder) {
//        this.username = builder.username;
//        this.password = builder.password;
//        this.email = builder.email;
//        this.phone = builder.phone;
//        this.address = builder.address;
//        this.fullName = builder.fullName;
//        this.claimList = new HashSet<>();
//    }
//
//    public Customer() {
//    }

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
        this.password = password;
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

    public Set<Claim> getClaimList() {
        return claimList;
    }

    public void setClaimList(HashSet<Claim> claimList) {
        this.claimList = claimList;
    }

    public void addClaim(Claim claim) {
        this.claimList.add(claim);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer ID: ").append(id).append("\n")
                .append("Username: ").append(username).append("\n")
                .append("Password: ").append(password).append("\n")
                .append("Email: ").append(email).append("\n")
                .append("Phone: ").append(phone).append("\n")
                .append("Address: ").append(address).append("\n")
                .append("Full Name: ").append(fullName).append("\n")
                .append("Claim List:\n");

        for (Claim claim : claimList) {
            sb.append("\t+ ").append(claim.getId()).append("\n");
        }

        return sb.toString();
    }


    public static abstract class GenericCustomerBuilder<T extends GenericCustomerBuilder<T>> {
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

        public abstract Customer build();
    }
}
