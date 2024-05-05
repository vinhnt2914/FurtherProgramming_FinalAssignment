package org.example.model.customer;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class PolicyHolder extends Beneficiary {
    @OneToMany(mappedBy = "policyHolder",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private Set<Dependant> dependantSet;

    public PolicyHolder(PolicyHolderBuilder builder) {
        super(builder);
        this.dependantSet = new HashSet<>();
    }

    public PolicyHolder() {
        super();
    }

    public void addDepdendants(Dependant... dependants) {
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
//    @Override
//    public String toString() {
//        return super();
//    }

    public static class PolicyHolderBuilder extends GenericBeneficaryBuilder<PolicyHolderBuilder> {

        @Override
        public PolicyHolder build() {
            return new PolicyHolder(this);
        }
    }
}
