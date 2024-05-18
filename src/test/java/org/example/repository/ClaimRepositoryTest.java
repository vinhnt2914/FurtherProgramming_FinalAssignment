package org.example.repository;

import org.checkerframework.checker.units.qual.C;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyOwner;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClaimRepositoryTest {

    @Test
    void findByID() {
        ClaimRepository repository = new ClaimRepository();
        assertNotNull(repository.findByID("f-0000000001"));
        repository.close();
    }

    @Test
    void getAll() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = repository.getAll();
        assertNotNull(claimList);
        repository.close();
    }

    @Test
    void getAllNew() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = repository.getAllNew();
        assertNotNull(claimList);
        assertTrue(claimList.stream().allMatch(claim -> claim.getStatus() == ClaimStatus.NEW));
        repository.close();
    }

    @Test
    void getAllProcessing() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = repository.getAllProcessing();
        assertNotNull(claimList);
        assertTrue(claimList.stream().allMatch(claim -> claim.getStatus() == ClaimStatus.PROCESSING));
        repository.close();
    }

    @Test
    void getAllDone() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = repository.getAllDone();
        assertNotNull(claimList);
        assertTrue(claimList.stream().allMatch(claim -> claim.getStatus() == ClaimStatus.DONE));
        repository.close();
    }

    @Test
    void getAllProcessingAndDone() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = repository.getAllProcessingAndDone();
        assertNotNull(claimList);
        assertTrue(claimList.stream().allMatch(claim ->
                claim.getStatus() == ClaimStatus.DONE || claim.getStatus() == ClaimStatus.PROCESSING));
        repository.close();
    }
}