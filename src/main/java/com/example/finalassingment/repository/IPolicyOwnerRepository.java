package com.example.finalassingment.repository;

import com.example.finalassingment.model.customer.PolicyOwner;

public interface IPolicyOwnerRepository  {
    void add(PolicyOwner policyOwner);
    void add(PolicyOwner... policyOwners);
}
