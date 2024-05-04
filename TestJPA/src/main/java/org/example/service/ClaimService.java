package org.example.service;

import org.example.model.Claim;

public class ClaimService {
    public Claim.ClaimBuilder makeClaim() {
        return new Claim.ClaimBuilder();
    }
}
