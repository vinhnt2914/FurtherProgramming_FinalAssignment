package org.example.repository;

import org.example.model.customer.Beneficiary;
import org.example.model.customer.Customer;

public interface IBeneficiaryRepository {
    void add(Beneficiary beneficiary);
    void add(Beneficiary... beneficiaries);
}
