package org.example.service;
/**
 * @author Group 11
 */
import org.example.model.items.Claim;

public class ClaimService {
    public Claim.ClaimBuilder makeClaim() {
        return new Claim.ClaimBuilder();
    }
}
