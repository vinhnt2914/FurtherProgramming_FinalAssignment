package org.example.model.customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.example.model.Beneficiary;

@Entity
public class Dependant extends Beneficiary {
    @ManyToOne
    private PolicyHolder policyHolder;

    public Dependant(DependantBuilder builder) {
        super(builder);
    }

    public Dependant() {

    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    @Override
    public String toString() {
        return String.format("Dependant[id: %s, name: %s, policyHolder: %s]",
                getId(),
                getFullName(),
                policyHolder.getId());
    }

    public static class DependantBuilder extends GenericBeneficaryBuilder<DependantBuilder> {

        @Override
        public Dependant build() {
            return new Dependant(this);
        }
    }
}
