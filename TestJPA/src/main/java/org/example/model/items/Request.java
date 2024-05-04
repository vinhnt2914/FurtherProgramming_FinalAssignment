package org.example.model.items;

import jakarta.persistence.*;
import org.example.model.customer.Customer;
import org.example.model.provider.InsuranceSurveyor;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private InsuranceSurveyor insuranceSurveyor;
    @ManyToOne
    private Customer customer;

    public Request(InsuranceSurveyor insuranceSurveyor, Customer customer) {
        this.insuranceSurveyor = insuranceSurveyor;
        this.customer = customer;
    }

    public Request() {

    }
}
