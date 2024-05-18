package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyOwner;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.model.provider.Provider;
import org.example.repository.EntityRepository;
import org.example.repository.IProviderRepository;

import java.util.ArrayList;
import java.util.List;

public class ProviderRepository extends EntityRepository implements IProviderRepository {
    @Override
    public void add(Provider provider) {
        em.getTransaction().begin();
        em.persist(provider);
        em.getTransaction().commit();
    }

    @Override
    public void add(Provider... providers) {
        em.getTransaction().begin();
        for (Provider p : providers) {
            em.persist(p);
        }
        em.getTransaction().commit();
    }

    @Override
    public Provider findByID(int id) {
       return em.find(Provider.class, id);
    }

    @Override
    public List<Provider> getAll() {
        List<InsuranceSurveyor> surveyorList = getAllSurveyor();
        List<InsuranceManager> managerList = getAllManager();
        List<Provider> res = new ArrayList<>();
        res.addAll(surveyorList);
        res.addAll(managerList);
        return res;
    }

    @Override
    public List<InsuranceSurveyor> getAllSurveyor() {
        TypedQuery<InsuranceSurveyor> query = em.createQuery(
                "from InsuranceSurveyor s " +
                        "join fetch s.manager m " +
                        "join fetch s.proposalSet ps " +
                        "join fetch ps.claim c1 " +
                        "join fetch s.requestSet rs " +
                        "join fetch rs.beneficiary b " +
                        "join fetch b.insuranceCard ic " +
                        "join fetch ic.policyOwner",
                InsuranceSurveyor.class);
        return query.getResultList();
    }

    @Override
    public List<InsuranceSurveyor> getAllSurveyorOfManager(InsuranceManager manager) {
        TypedQuery<InsuranceSurveyor> query = em.createQuery("from InsuranceSurveyor s where s.manager = :manager", InsuranceSurveyor.class);
        query.setParameter("manager", manager);
        return query.getResultList();
    }

    @Override
    public List<InsuranceManager> getAllManager() {
        TypedQuery<InsuranceManager> query = em.createQuery(
                "from InsuranceManager m " +
                        "join fetch m.proposalSet ps " +
                        "join fetch ps.claim c " +
                        "join fetch ps.insuranceSurveyor",
                InsuranceManager.class);
        return query.getResultList();
    }

    @Override
    public Provider removeByID(int id) {
        em.getTransaction().begin();
        Provider providerToRemove = em.find(Provider.class, id);
        em.remove(providerToRemove);
        em.getTransaction().commit();

        return providerToRemove;
    }
}
