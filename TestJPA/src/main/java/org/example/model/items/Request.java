package org.example.model.items;

import jakarta.persistence.*;
import org.example.model.customer.Beneficiary;
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
    private Beneficiary customer;
    private String message;
    public Request(InsuranceSurveyor insuranceSurveyor, Beneficiary customer, String message) {
        this.insuranceSurveyor = insuranceSurveyor;
        this.customer = customer;
        this.message = message;
    }

    public Request() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Beneficiary customer) {
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", insuranceSurveyor=" + insuranceSurveyor.getId() +
                ", customer=" + customer.getId() +
                ", message='" + message + '\'' +
                '}';
    }
}
