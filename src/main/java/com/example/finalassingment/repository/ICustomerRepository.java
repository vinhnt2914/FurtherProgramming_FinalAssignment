package com.example.finalassingment.repository;

import com.example.finalassingment.model.customer.*;

import java.util.List;

public interface ICustomerRepository {
    void add(Customer customer);
    void add(Customer... customers);
    Customer findByID(int id);
    List<Customer> getAll();
    List<Dependant> getAllDependants();
    List<Dependant> getAllDependantsOfPolicyHolder(PolicyHolder policyHolder);
    List<Dependant> getAllDependantsOfPolicyOwner(PolicyOwner policyOwner);
    List<PolicyHolder> getAllPolicyHolders();
    List<PolicyHolder> getAllPolicyHoldersOfPolicyOwner(PolicyOwner policyOwner);
    List<Beneficiary> getAllBeneficiaryOfPolicyOwner(PolicyOwner policyOwner);
    List<Beneficiary> getAllPolicyHoldersAndDependants();
    List<PolicyOwner> getAllPolicyOwners();
    void update(Customer customer);
    Customer removeByID(int id);
}
