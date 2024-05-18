package org.example.model.provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class InsuranceManager extends Provider {
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "insuranceManager")
    private Set<Proposal> proposalSet;

    public InsuranceManager(ManagerBuilder builder) {
        super(builder);
        proposalSet = new HashSet<>();
    }

    public InsuranceManager() {
    }

    public void approveClaim(Claim claim) {
        claim.setStatus(ClaimStatus.PROCESSING);
    }

    public void rejectClaim(Claim claim) {
        claim.setStatus(ClaimStatus.REJECTED);
    }

    public static class ManagerBuilder extends GenericProviderBuilder<ManagerBuilder> {
        @Override
        public InsuranceManager build() {
            return new InsuranceManager(this);
        }
    }

    public Set<Proposal> getProposalList() {
        return proposalSet;
    }

    public List<Integer> getProposalIDs() {
        List<Integer> idList = new ArrayList<>();
        for (Proposal p : proposalSet) idList.add(p.getId());

        return idList;
    }
}
