package com.example.finalassingment.repository.impl;

import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IInsuranceSurveyorRepository;

import java.util.List;

public class InsuranceSurveyorRepository extends EntityRepository implements IInsuranceSurveyorRepository {
    @Override
    public void add(InsuranceSurveyor insuranceSurveyor) {
        em.getTransaction().begin();
        em.persist(insuranceSurveyor);
        em.getTransaction().commit();
    }

    @Override
    public void add(InsuranceSurveyor... insuranceSurveyors) {
        em.getTransaction().begin();
        for (InsuranceSurveyor s : insuranceSurveyors) {
            em.persist(s);
        }
        em.getTransaction().commit();
    }

    @Override
    public InsuranceSurveyor findByID(int id) {
        return em.find(InsuranceSurveyor.class, id);
    }

    @Override
    public List<InsuranceSurveyor> getAll() {
        TypedQuery<InsuranceSurveyor> query = em.createQuery("from InsuranceSurveyor ", InsuranceSurveyor.class);
        return query.getResultList();
    }

    @Override
    public InsuranceSurveyor removeByID(int id) {
        em.getTransaction().begin();

        InsuranceSurveyor insuranceSurveyorToRemove = em.find(InsuranceSurveyor.class, id);
        em.remove(insuranceSurveyorToRemove);

        em.getTransaction().commit();
        return insuranceSurveyorToRemove;
    }
}
