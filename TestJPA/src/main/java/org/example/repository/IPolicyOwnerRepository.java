package org.example.repository;

import org.example.model.customer.Beneficiary;
import org.example.model.customer.PolicyOwner;
import org.example.repository.EntityRepository;

public interface IPolicyOwnerRepository  {
    void add(PolicyOwner policyOwner);
    void add(PolicyOwner... policyOwners);
}
