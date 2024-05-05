package org.example.repository.impl;

import jakarta.persistence.Inheritance;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.Customer;
import org.example.repository.EntityRepository;
import org.example.repository.IBeneficiaryRepository;

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
