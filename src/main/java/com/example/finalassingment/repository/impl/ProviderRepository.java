package com.example.finalassingment.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.model.provider.Provider;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IProviderRepository;

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
        TypedQuery<InsuranceSurveyor> query = em.createQuery("SELECT s FROM InsuranceSurveyor s", InsuranceSurveyor.class);

        EntityGraph<InsuranceSurveyor> entityGraph = em.createEntityGraph(InsuranceSurveyor.class);
        entityGraph.addAttributeNodes("manager", "proposalSet", "requestSet");
        entityGraph.addSubgraph("proposalSet").addAttributeNodes("claim", "insuranceSurveyor");
        entityGraph.addSubgraph("requestSet").addAttributeNodes("beneficiary");
        entityGraph.addSubgraph("requestSet").addSubgraph("beneficiary").addAttributeNodes("insuranceCard");
        entityGraph.addSubgraph("requestSet").addSubgraph("beneficiary").addSubgraph("insuranceCard").addAttributeNodes("policyOwner");

        query.setHint("jakarta.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }


    @Override
    public List<InsuranceSurveyor> getAllSurveyorOfManager(InsuranceManager manager) {
        TypedQuery<InsuranceSurveyor> query = em.createQuery(
                "SELECT s FROM InsuranceSurveyor s WHERE s.manager = :manager", InsuranceSurveyor.class);
        query.setParameter("manager", manager);

        EntityGraph<InsuranceSurveyor> entityGraph = em.createEntityGraph(InsuranceSurveyor.class);
        entityGraph.addAttributeNodes("manager", "proposalSet");
        entityGraph.addSubgraph("proposalSet").addAttributeNodes("claim");

        query.setHint("jakarta.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }


    @Override
    public List<InsuranceManager> getAllManager() {
        TypedQuery<InsuranceManager> query = em.createQuery("SELECT m FROM InsuranceManager m", InsuranceManager.class);
        EntityGraph<InsuranceManager> entityGraph = em.createEntityGraph(InsuranceManager.class);
        entityGraph.addAttributeNodes("proposalSet");
        entityGraph.addSubgraph("proposalSet").addAttributeNodes("claim", "insuranceSurveyor");
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);

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
