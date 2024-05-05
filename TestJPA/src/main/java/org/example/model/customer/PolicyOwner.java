package org.example.model.customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.example.repository.impl.CustomerRepository;

import java.util.HashSet;
import java.util.Set;

@Entity
public class PolicyOwner extends Customer {
    @OneToMany(mappedBy = "policyOwner",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Beneficiary> beneficiarySet;

    public PolicyOwner(PolicyOwnerBuilder builder) {
        super(builder);
        this.beneficiarySet = new HashSet<>();
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

    public static class PolicyOwnerBuilder extends GenericCustomerBuilder<PolicyOwnerBuilder> {
        protected Set<Beneficiary> beneficiaries;
        @Override
        public PolicyOwner build() {
            return new PolicyOwner(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("PolicyOwner[id: %s, name: %s]", getId(), getFullName()));
//        sb.append("\nBeneficiaries:\n");
//        for (Customer beneficiary : beneficiarySet) {
//            sb.append(String.format("    %s\n", beneficiary.toString()));
//        }
        return sb.toString();
    }

}
