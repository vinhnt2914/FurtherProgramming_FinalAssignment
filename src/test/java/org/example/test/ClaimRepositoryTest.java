package org.example.test;

import org.checkerframework.checker.units.qual.C;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClaimRepositoryTest {

    @Test
    void getAllNew() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> newClaimList = repository.getAllNew();
        repository.close();
        assertNotNull(newClaimList);
    }

    @Test
    void getAllProcessing() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> newClaimList = repository.getAllProcessing();
        repository.close();
        assertNotNull(newClaimList);
    }

    @Test
    void getAllDone() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> newClaimList = repository.getAllDone();
        repository.close();
        assertNotNull(newClaimList);
    }
}