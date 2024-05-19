package org.example.repository;
/**
 * @author Group 11
 */
import org.example.model.customer.Beneficiary;

public interface IBeneficiaryRepository {
    void add(Beneficiary beneficiary);
    void add(Beneficiary... beneficiaries);
}
