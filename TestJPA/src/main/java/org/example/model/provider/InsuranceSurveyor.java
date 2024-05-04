package org.example.model.provider;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.model.customer.Customer;
import org.example.model.items.Request;

import java.util.List;

@Entity
public class InsuranceSurveyor extends GenericInsuranceUser{
    @OneToMany(mappedBy = "insuranceSurveyor")
    private List<Request> requestList;
    public InsuranceSurveyor(String username, String password) {
        super(username, password);
    }

    public InsuranceSurveyor() {
    }

    public Request makeRequest(Customer customer) {
        return new Request(this, customer);
    }
}
