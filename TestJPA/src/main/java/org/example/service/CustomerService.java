package org.example.service;
/**
 * @author Group 11
 */
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;

public class CustomerService {
    public Dependant.DependantBuilder makeDependant() {
        return new Dependant.DependantBuilder();
    }

    public PolicyHolder.PolicyHolderBuilder makePolicyHolder() {
        return new PolicyHolder.PolicyHolderBuilder();
    }

    public PolicyOwner.PolicyOwnerBuilder makePolicyOwner() {
        return new PolicyOwner.PolicyOwnerBuilder();
    }

}
