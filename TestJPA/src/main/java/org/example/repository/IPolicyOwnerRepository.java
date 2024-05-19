package org.example.repository;

/**
 * @author Group 11
 */
import org.example.model.customer.PolicyOwner;


public interface IPolicyOwnerRepository  {
    void add(PolicyOwner policyOwner);
    void add(PolicyOwner... policyOwners);
}
