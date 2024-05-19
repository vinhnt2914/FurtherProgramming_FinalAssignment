package com.example.finalassingment.director;

import com.example.finalassingment.model.items.Claim;

public class ClaimDirector {
    public Claim.ClaimBuilder makeClaim() {
        return new Claim.ClaimBuilder();
    }
}
