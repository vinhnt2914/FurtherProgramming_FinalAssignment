package org.example.model.customer;

public class BeneficiaryBuilder extends Beneficiary.GenericBeneficaryBuilder<BeneficiaryBuilder> {
    @Override
    public Beneficiary build() {
        return new Beneficiary(this);
    }
}
