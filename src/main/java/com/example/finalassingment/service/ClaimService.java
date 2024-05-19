package com.example.finalassingment.service;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.items.Claim;

public class ClaimService {
    public Claim.ClaimBuilder makeClaim() {
        return new Claim.ClaimBuilder();
    }
}
