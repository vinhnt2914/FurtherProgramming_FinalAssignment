package org.example.repository;

import org.example.model.customer.PolicyHolder;
import org.example.model.items.Claim;

import java.util.List;

public interface IClaimRepository {
    void add(Claim claim);
    void add(Claim... claims);
    Claim findByID(String id);
    List<Claim> getAll();
    void update(Claim claim);
    Claim removeByID(String id);
    List<Claim> getClaimsByPolicyHolder(PolicyHolder policyHolder);
}
