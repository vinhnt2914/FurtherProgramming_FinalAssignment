package org.example.model.items;

import jakarta.persistence.*;
import org.example.model.customer.Customer;
import org.example.model.provider.InsuranceSurveyor;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private InsuranceSurveyor insuranceSurveyor;
    @ManyToOne
    private Customer customer;
    private String message;
    public Request(InsuranceSurveyor insuranceSurveyor, Customer customer, String message) {
        this.insuranceSurveyor = insuranceSurveyor;
        this.customer = customer;
        this.message = message;
    }

    public Request() {

    }
}
