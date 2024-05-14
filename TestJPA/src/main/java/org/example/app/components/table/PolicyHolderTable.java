package org.example.app.components.table;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;

import java.util.List;

@Entity
public class PolicyHolderTable extends Beneficiary {
    @OneToMany(mappedBy = "policyHolder", fetch = FetchType.LAZY)
    private List<Dependant> dependants;

    public PolicyHolder(PolicyHolderBuilder builder) {
        super(builder);
    }

    public PolicyHolder() {
    }

    public List<Dependant> getDependants() {
        return dependants;
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }

    @Override
    public String toString() {
        return String.format("PolicyHolder[id: %s, name: %s, dependants: %d]",
                getId(),
                getFullName(),
                dependants != null ? dependants.size() : 0);
    }

    public static class PolicyHolderBuilder extends GenericBeneficaryBuilder<PolicyHolderBuilder> {

        @Override
        public PolicyHolder build() {
            return new PolicyHolder(this);
        }
    }
}
