package com.example.finalassingment.repository.impl;

import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IBeneficiaryRepository;

public class BeneficiaryRepository extends EntityRepository implements IBeneficiaryRepository {
    @Override
    public void add(Beneficiary beneficiary) {
        em.getTransaction().begin();
        em.persist(beneficiary);
        em.getTransaction().commit();
    }

    @Override
    public void add(Beneficiary... beneficiaries) {
        em.getTransaction().begin();
        for (Beneficiary b : beneficiaries) {
            em.persist(b);
        }
        em.getTransaction().commit();
    }
}
