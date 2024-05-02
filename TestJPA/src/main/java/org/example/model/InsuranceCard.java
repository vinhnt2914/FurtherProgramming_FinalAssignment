package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.example.model.customer.Customer;

@Entity
public class InsuranceCard {
    @Id
    private String id;
    @OneToOne(mappedBy = "insuranceCard")
    private Customer customer;
    public InsuranceCard(String id) {
        this.id = id;
    }

    public InsuranceCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "InsuranceCard{" +
                "id='" + id + '\'' +
                ", customer=" + customer.getId() +
                '}';
    }
}
