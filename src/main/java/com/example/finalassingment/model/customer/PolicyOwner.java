package com.example.finalassingment.model.customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.example.finalassingment.repository.impl.CustomerRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PolicyOwner extends Customer {
    @OneToMany(mappedBy = "policyOwner",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<PolicyHolder> policyHolderSet;
    private double fee;
    public PolicyOwner(PolicyOwnerBuilder builder) {
        super(builder);
        this.policyHolderSet = new HashSet<>();
        this.fee = builder.fee;
    }
    public PolicyOwner() {
    }

    public void addPolicyHolders(PolicyHolder... policyHolders) {
        policyHolderSet.addAll(Arrays.asList(policyHolders));
    }

    public Set<PolicyHolder> getPolicyHolderSet() {
        return policyHolderSet;
    }

    public int calculateTotalBeneficiaries() {
        int total = policyHolderSet.size();
        for (PolicyHolder ph : policyHolderSet) {
            total += ph.getDependantSet().size();
        }
        return total;
    }

    public double calculateFee() {
        CustomerRepository repository = new CustomerRepository();
        double totalCost = fee;
        List<PolicyHolder> policyHolderList = repository.getAllPolicyHoldersOfPolicyOwner(this);
        for (PolicyHolder h : policyHolderList) {
            totalCost += fee;
            for (Dependant d : h.getDependantSet()) {
                totalCost += ((fee/100)*60);
            }
        }
        repository.close();
        return totalCost;
    }

    public static class PolicyOwnerBuilder extends GenericCustomerBuilder<PolicyOwnerBuilder> {
        protected Set<Beneficiary> beneficiaries;
        protected double fee;
        public PolicyOwnerBuilder fee(double fee) {
            this.fee = fee;
            return self();
        }
        @Override
        public PolicyOwner build() {
            return new PolicyOwner(this);
        }
    }

    public double getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return "PolicyOwner{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
