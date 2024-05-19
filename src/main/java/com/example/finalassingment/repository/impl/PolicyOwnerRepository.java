package com.example.finalassingment.repository.impl;

import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IPolicyOwnerRepository;

public class PolicyOwnerRepository extends EntityRepository implements IPolicyOwnerRepository {
    @Override
    public void add(PolicyOwner policyOwner) {
        em.getTransaction().begin();
        em.persist(policyOwner);
        em.getTransaction().commit();
    }

    @Override
    public void add(PolicyOwner... policyOwners) {
        em.getTransaction().begin();
        for (PolicyOwner o : policyOwners) {
            em.persist(o);
        }
        em.getTransaction().commit();
    }
}
