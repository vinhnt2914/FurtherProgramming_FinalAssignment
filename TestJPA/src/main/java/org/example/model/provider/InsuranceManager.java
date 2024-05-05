package org.example.model.provider;

import org.example.model.customer.Customer;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;

public class InsuranceManager extends GenericInsuranceUser {
    public InsuranceManager(String username, String password) {
        super(username, password);
    }

    public void approveClaim(Claim claim) {
        claim.setStatus(ClaimStatus.PROCESSING);
    }

    public void rejectClaim(Claim claim) {
        claim.setStatus(ClaimStatus.REJECTED);
    }
}
