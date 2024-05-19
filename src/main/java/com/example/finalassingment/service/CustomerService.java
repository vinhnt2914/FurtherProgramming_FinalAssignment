package com.example.finalassingment.service;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.customer.Dependant;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.customer.PolicyOwner;

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
