package org.example.model.customer;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public static class PolicyHolderBuilder extends GenericBeneficaryBuilder<PolicyHolderBuilder> {

        @Override
        public PolicyHolder build() {
            return new PolicyHolder(this);
        }
    }
}
