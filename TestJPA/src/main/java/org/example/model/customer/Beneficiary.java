package org.example.model.customer;

import jakarta.persistence.*;
import org.example.model.items.Claim;
import org.example.model.items.InsuranceCard;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Beneficiary extends Customer {
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "policy_owner_id")
    private PolicyOwner policyOwner;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "cardHolder",
            fetch=FetchType.LAZY) // When persist customer, we persist the card as well
    protected InsuranceCard insuranceCard;
    @OneToMany(mappedBy = "insuredPerson",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch=FetchType.LAZY)
    private Set<Claim> claimList;

    public Beneficiary(GenericBeneficaryBuilder builder) {
        super(builder);
        this.claimList = new HashSet<>();
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
    public Set<Claim> getClaimList() {
        return claimList;
    }

    public void setClaimList(HashSet<Claim> claimList) {
        this.claimList = claimList;
        for (Claim c : claimList) c.setInsuredPerson(this);
    }
    public void addClaim(Claim claim) {
        this.claimList.add(claim);
        claim.setInsuredPerson(this);
    }


    public abstract static class GenericBeneficaryBuilder<T extends GenericBeneficaryBuilder<T>> extends GenericCustomerBuilder<T> {
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
                "id=" + id +
                '}';
    }
}
