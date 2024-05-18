package org.example.model.provider;

import jakarta.persistence.*;
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

    private Set<Request> requestSet;
    @OneToMany(mappedBy = "insuranceSurveyor")
    private Set<Proposal> proposalSet;
    @ManyToOne
    private InsuranceManager manager;

    public InsuranceSurveyor(SurveyorBuilder builder) {
        super(builder);
        this.manager = builder.manager;
        requestSet = new HashSet<>();
    }

    public InsuranceSurveyor() {
    }

    public Request makeRequest(PolicyHolder policyHolder, Claim claim, String message) {
        Request request = new Request(this, policyHolder, claim, message);
        return request;
    }

    public Proposal propose(InsuranceManager insuranceManager, Claim claim, String message) {
        return new Proposal(this, claim, insuranceManager, message);
    }

    public static class SurveyorBuilder extends GenericProviderBuilder<SurveyorBuilder> {
        protected InsuranceManager manager;
        public SurveyorBuilder manager(InsuranceManager manager) {
            this.manager = manager;
            return self();
        }
        @Override
        public InsuranceSurveyor build() {
            return new InsuranceSurveyor(this);
        }
    }

    public Set<Request> getRequestSet() {
        return requestSet;
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
        for (Request p : requestSet) idList.add(p.getId());

        return idList;
    }

    public InsuranceManager getManager() {
        return manager;
    }

    public void setManager(InsuranceManager manager) {
        this.manager = manager;
    }
}
