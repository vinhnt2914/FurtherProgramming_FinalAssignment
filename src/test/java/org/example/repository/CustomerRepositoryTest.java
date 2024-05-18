package org.example.repository;

import org.checkerframework.checker.units.qual.C;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    @Test
    void shouldGetAllDependants() {
        CustomerRepository repository = new CustomerRepository();
        List<Dependant> dependantList = repository.getAllDependants();
        assertNotNull(dependantList);
        assertTrue(dependantList.stream().allMatch(customer -> customer instanceof Dependant));
        repository.close();
    }

    @Test
    void shouldGetAllPolicyHolders() {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyHolder> policyHolderList = repository.getAllPolicyHolders();
        assertNotNull(policyHolderList);
        assertTrue(policyHolderList.stream().allMatch(customer -> customer instanceof PolicyHolder));
        repository.close();
    }

    @Test
    void shouldGetAllPolicyOwners() {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyOwner> policyOwnerList = repository.getAllPolicyOwners();
        assertNotNull(policyOwnerList);
        assertTrue(policyOwnerList.stream().allMatch(customer -> customer instanceof PolicyOwner));
        repository.close();
    }
}