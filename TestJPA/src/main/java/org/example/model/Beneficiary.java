package org.example.model;

import jakarta.persistence.*;
import org.example.model.customer.Customer;
import org.example.model.customer.PolicyOwner;

@Entity
@Table(name = "policyOwner_beneficiary")
public abstract class Beneficiary extends Customer {
    @ManyToOne
    @JoinColumn(name = "policy_owner_id")
    private PolicyOwner policyOwner;

    public Beneficiary(GenericBeneficaryBuilder builder) {
        super(builder);
    }

    public Beneficiary() {
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public abstract static class GenericBeneficaryBuilder<T extends GenericBeneficaryBuilder<T>> extends GenericCustomerBuilder<T> {
    }

}
