package org.example.repository.impl;

import org.example.model.customer.Beneficiary;
import org.example.model.customer.PolicyOwner;
import org.example.repository.EntityRepository;
import org.example.repository.IPolicyOwnerRepository;

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
