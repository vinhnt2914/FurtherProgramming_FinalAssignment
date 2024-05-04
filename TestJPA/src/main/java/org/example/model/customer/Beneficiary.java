package org.example.model.customer;

import jakarta.persistence.*;
import org.example.model.items.InsuranceCard;

@Entity
@Table(name = "policyOwner_beneficiary")
public class Beneficiary extends Customer {
    @ManyToOne
    @JoinColumn(name = "policy_owner_id")
    private PolicyOwner policyOwner;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) // When persist customer, we persist the card as well
    protected InsuranceCard insuranceCard;

    public Beneficiary(GenericBeneficaryBuilder builder) {
        super(builder);
    }

    public Beneficiary() {
        super();
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }
    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
        insuranceCard.setCardHolder(this);
        insuranceCard.setPolicyOwner(policyOwner);
    }

    public abstract static class GenericBeneficaryBuilder<T extends GenericBeneficaryBuilder<T>> extends GenericCustomerBuilder<T> {
    }

}
