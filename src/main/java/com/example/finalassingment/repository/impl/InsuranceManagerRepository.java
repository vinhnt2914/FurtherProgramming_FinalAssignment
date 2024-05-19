package com.example.finalassingment.repository.impl;

import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IInsuranceManagerRepository;

import java.util.List;

public class InsuranceManagerRepository extends EntityRepository implements IInsuranceManagerRepository {
    @Override
    public void add(InsuranceManager insuranceManager) {
        em.getTransaction().begin();
        em.persist(insuranceManager);
        em.getTransaction().commit();
    }

    @Override
    public void add(InsuranceManager... insuranceManagers) {
        em.getTransaction().begin();
        for (InsuranceManager m : insuranceManagers) {
            em.persist(m);
        }
        em.getTransaction().commit();
    }

    @Override
    public InsuranceManager findByID(int id) {
        return em.find(InsuranceManager.class, id);
    }

    @Override
    public List<InsuranceManager> getAll() {
        TypedQuery<InsuranceManager> query = em.createQuery("from InsuranceManager", InsuranceManager.class);
        return query.getResultList();
    }

    @Override
    public InsuranceManager removeByID(int id) {
        em.getTransaction().begin();

        InsuranceManager managerToRemove = em.find(InsuranceManager.class, id);
        em.remove(managerToRemove);

        em.getTransaction().commit();
        return managerToRemove;
    }
}
