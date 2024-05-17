package org.example.model.customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.example.model.items.InsuranceCard;
import org.example.repository.impl.CustomerRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PolicyOwner extends Customer {
    @OneToMany(mappedBy = "policyOwner",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Beneficiary> beneficiarySet;
    private double fee;
    public PolicyOwner(PolicyOwnerBuilder builder) {
        super(builder);
        this.beneficiarySet = new HashSet<>();
        this.fee = builder.fee;
    }
    public PolicyOwner() {
    }

    public void addBeneficaries(Beneficiary... beneficiaries) {
        for (Beneficiary c : beneficiaries) {
            if (c instanceof PolicyHolder) {
                beneficiarySet.add(c);
                c.setPolicyOwner(this);
                for (Dependant d : ((PolicyHolder) c).getDependantSet()) {
                    beneficiarySet.add(d);
                    d.setPolicyOwner(this);
                }
            }
        }
    }

    public Set<Beneficiary> getBeneficiarySet() {
        return beneficiarySet;
    }

    public Beneficiary removeBeneficiary(Beneficiary beneficiary) {
        CustomerRepository repo = new CustomerRepository();
        beneficiarySet.remove(beneficiary);
        repo.removeByID(beneficiary.getId());
        repo.close();
        return beneficiary;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("PolicyOwner[id: %s, name: %s]", getId(), getFullName()));

        return sb.toString();
    }

}
