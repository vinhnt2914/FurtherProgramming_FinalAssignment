package org.example.model.provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;
import org.example.model.items.Request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class InsuranceSurveyor extends Provider {
    @OneToMany(
            mappedBy = "insuranceSurveyor",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})

    private Set<Request> requestList;
    @OneToMany(mappedBy = "insuranceSurveyor")
    private Set<Proposal> proposalSet;

    public InsuranceSurveyor(SurveyorBuilder builder) {
        super(builder);
        requestList = new HashSet<>();
    }

    public InsuranceSurveyor() {
    }

    public Request makeRequest(Beneficiary customer, String message) {
        Request request = new Request(this, customer, message);
        requestList.add(request); // Add to the request list
        return request;
    }

    public Proposal propose(InsuranceManager insuranceManager, Claim claim, String message) {
        return new Proposal(this, claim, insuranceManager, message);
    }

    public static class SurveyorBuilder extends GenericProviderBuilder<SurveyorBuilder> {
        @Override
        public InsuranceSurveyor build() {
            return new InsuranceSurveyor(this);
        }
    }

    public Set<Request> getRequestList() {
        return requestList;
    }

    public Set<Proposal> getProposalSet() {
        return proposalSet;
    }

    public List<Integer> getProposalIDs() {
        List<Integer> idList = new ArrayList<>();
        for (Proposal p : proposalSet) idList.add(p.getId());

        return idList;
    }

    public List<Integer> getRequestIDs() {
        List<Integer> idList = new ArrayList<>();
        for (Request p : requestList) idList.add(p.getId());

        return idList;
    }
}
