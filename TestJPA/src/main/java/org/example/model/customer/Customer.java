package org.example.model.customer;

import jakarta.persistence.*;
import org.example.model.InsuranceCard;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "fullName")
    private String fullName;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) // When persist customer, we persist the card as well
    private InsuranceCard insuranceCard;
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<org.example.model.Claim> claimList;

    public Customer(GenericCustomerBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.fullName = builder.fullName;
        this.insuranceCard = builder.insuranceCard;
        this.claimList = new HashSet<>();
//        insuranceCard.setCustomer(this);
    }

    public Customer() {
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

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public Set<org.example.model.Claim> getClaimList() {
        return claimList;
    }

    public void setClaimList(HashSet<org.example.model.Claim> claimList) {
        this.claimList = claimList;
    }

    public void addClaim(org.example.model.Claim claim) {
        this.claimList.add(claim);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", fullName='" + fullName + '\'' +
                ", insuranceCard=" + insuranceCard +
                '}';
    }

    public static abstract class GenericCustomerBuilder<T extends GenericCustomerBuilder<T>> {
        protected String username;
        protected String password;
        protected String email;
        protected String phone;
        protected String address;
        protected String fullName;
        protected InsuranceCard insuranceCard;
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
        public T insuranceCard(InsuranceCard insuranceCard) {
            this.insuranceCard = insuranceCard;
            return self();
        }

        public abstract Customer build();
    }
}
