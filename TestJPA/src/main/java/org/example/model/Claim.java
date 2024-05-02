package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.example.model.customer.Customer;

@Entity
public class Claim {
    @Id
    private String id;
    private String content;
    @ManyToOne
    private Customer customer;

    public Claim(String id, String content, Customer customer) {
        this.id = id;
        this.content = content;
        this.customer = customer;
        customer.addClaim(this);
    }
    public Claim() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", customer=" + customer.getId() +
                '}';
    }
}
