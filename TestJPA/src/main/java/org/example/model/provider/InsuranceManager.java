package org.example.model.provider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;

import java.util.ArrayList;
import java.util.List;

@Entity
public class InsuranceManager extends Provider {
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "insuranceManager")
    private List<Proposal> proposalList;
//    public InsuranceManager(String username, String password) {
//        super(username, password);
//    }


    public InsuranceManager(ManagerBuilder builder) {
        super(builder);
        proposalList = new ArrayList<>();
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

    public List<Proposal> getProposalList() {
        return proposalList;
    }

    public List<Integer> getProposalIDs() {
        List<Integer> idList = new ArrayList<>();
        for (Proposal p : proposalList) idList.add(p.getId());

        return idList;
    }
}
