package com.example.finalassingment.model.customer;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class PolicyHolder extends Beneficiary {
    @ManyToOne(cascade = {CascadeType.MERGE})
    private PolicyOwner policyOwner;
    @OneToMany(mappedBy = "policyHolder",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Dependant> dependantSet;

    public PolicyHolder(PolicyHolderBuilder builder) {
        super(builder);
        this.dependantSet = new HashSet<>();
        this.policyOwner = builder.policyOwner;
    }

    public PolicyHolder() {
        super();
    }

    public void addDependants(Dependant... dependants) {
        for (Dependant d : dependants) {
            this.dependantSet.add(d);
            d.setPolicyHolder(this);
        }
    }

    public Set<Dependant> getDependantSet() {
        return dependantSet;
    }

    public void setDependantSet(Set<Dependant> dependantSet) {
        this.dependantSet = dependantSet;
    }
    public List<Integer> getDependantsIds() {
        List<Integer> idList = new ArrayList<>();
        for (Dependant d: dependantSet) idList.add(d.getId());
        return idList;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public static class PolicyHolderBuilder extends GenericBeneficaryBuilder<PolicyHolderBuilder> {
        protected PolicyOwner policyOwner;
        public PolicyHolderBuilder policyOwner(PolicyOwner policyOwner) {
            this.policyOwner = policyOwner;
            return self();
        }
        @Override
        public PolicyHolder build() {
            return new PolicyHolder(this);
        }
    }
}
