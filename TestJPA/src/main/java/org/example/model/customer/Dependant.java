package org.example.model.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Dependant extends Beneficiary {
    @ManyToOne(fetch = FetchType.LAZY)
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

    public int getId() {
        return super.getId();
    }

    public String getFullName() {
        return super.getFullName();
    }

    @Override
    public String toString() {
        return String.format("Dependant[id: %d, name: %s, policyHolder: %d]",
                getId(),
                getFullName(),
                policyHolder != null ? policyHolder.getId() : null);
    }

    public static class DependantBuilder extends GenericBeneficaryBuilder<DependantBuilder> {

        @Override
        public Dependant build() {
            return new Dependant(this);
        }
    }
}
