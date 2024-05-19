package org.example.repository;

import com.example.finalassingment.model.customer.Dependant;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.impl.CustomerRepository;
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