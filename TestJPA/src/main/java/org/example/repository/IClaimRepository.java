package org.example.repository;

import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.items.Claim;

import java.util.List;

public interface IClaimRepository {
    void add(Claim claim);
    void add(Claim... claims);
    Claim findByID(String id);
    List<Claim> getAll();
    List<Claim> getAllNew();
    List<Claim> getAllProcessing();
    List<Claim> getAllDone();
    List<Claim> getAllProcessingAndDone();
    void update(Claim claim);
    Claim removeByID(String id);
    List<Claim> getClaimsOfDependant(Dependant dependant);
    List<Claim> getClaimsOfPolicyHolder(PolicyHolder policyHolder);
    List<Claim> getClaimsOfPolicyHolderAndTheirDependants(PolicyHolder policyHolder);

}
