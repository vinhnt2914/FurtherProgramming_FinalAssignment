package org.example.model.provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;

import java.util.List;

@Entity
public class InsuranceManager extends Provider {
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "insuranceManager")
    private List<Proposal> proposalList;
    public InsuranceManager(String username, String password) {
        super(username, password);
    }

    public InsuranceManager() {
    }

    public void approveClaim(Claim claim) {
        claim.setStatus(ClaimStatus.PROCESSING);
    }

    public void rejectClaim(Claim claim) {
        claim.setStatus(ClaimStatus.REJECTED);
    }

    @Override
    public String toString() {
        return "InsuranceManager{" +
                "proposalList=" + proposalList.toString() +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
