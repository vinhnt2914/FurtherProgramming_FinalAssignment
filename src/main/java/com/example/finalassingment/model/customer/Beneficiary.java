package com.example.finalassingment.model.customer;
/**
 * @author Group 11
 */
import jakarta.persistence.*;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.model.items.InsuranceCard;
import com.example.finalassingment.model.items.Request;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Beneficiary extends Customer {
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "cardHolder") // When persist customer, we persist the card as well
    protected InsuranceCard insuranceCard;
    @OneToMany(mappedBy = "insuredPerson",
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    protected Set<Claim> claimList;
    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.REMOVE)
    protected Set<Request> requestList;
    public Beneficiary(GenericBeneficaryBuilder builder) {
        super(builder);
        this.claimList = new HashSet<>();
    }

    public Beneficiary() {
        super();
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }
    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
        insuranceCard.setCardHolder(this);
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

    public PolicyOwner getPolicyOwner() {
        if (this instanceof Dependant dependant) {
            return dependant.getPolicyOwner();
        } else if (this instanceof PolicyHolder policyHolder) {
            return policyHolder.getPolicyOwner();
        }
        return null;
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
