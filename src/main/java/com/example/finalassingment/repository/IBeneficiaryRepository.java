package com.example.finalassingment.repository;

import com.example.finalassingment.model.customer.Beneficiary;

public interface IBeneficiaryRepository {
    void add(Beneficiary beneficiary);
    void add(Beneficiary... beneficiaries);
}
