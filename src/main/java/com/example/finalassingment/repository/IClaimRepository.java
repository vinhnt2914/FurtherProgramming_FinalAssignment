package com.example.finalassingment.repository;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.customer.Dependant;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.items.Claim;

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
