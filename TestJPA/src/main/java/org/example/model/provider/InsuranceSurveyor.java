package org.example.model.provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.model.customer.Customer;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;
import org.example.model.items.Request;

import java.util.HashSet;
import java.util.Set;

@Entity
public class InsuranceSurveyor extends Provider {
    @OneToMany(
            mappedBy = "insuranceSurveyor",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Request> requestList;
    public InsuranceSurveyor(String username, String password) {
        super(username, password);
        requestList = new HashSet<>();
    }

    public InsuranceSurveyor() {
    }

    public Request makeRequest(Customer customer, String content) {
        Request request = new Request(this, customer, content);
        requestList.add(request); // Add to the request list
        return request;
    }

    public Proposal propose(InsuranceManager insuranceManager, Claim claim, String message) {
        return new Proposal(this, claim, insuranceManager, message);
    }
}
