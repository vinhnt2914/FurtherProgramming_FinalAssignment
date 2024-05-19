package com.example.finalassingment.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.customer.Dependant;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.enums.ClaimStatus;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IClaimRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClaimRepository extends EntityRepository implements IClaimRepository {
    @Override
    public void add(Claim claim) {
        em.getTransaction().begin();
        em.persist(claim);
        em.getTransaction().commit();
    }

    @Override
    public void add(Claim... claims) {
        em.getTransaction().begin();
        for (Claim c : claims) {
            em.persist(c);
        }
        em.getTransaction().commit();
    }

    @Override
    public Claim findByID(String id) {
        return em.find(Claim.class, id);
    }

    @Override
    public List<Claim> getAll() {
        TypedQuery<Claim> query = em.createQuery("from Claim ", Claim.class);
        EntityGraph<Claim> entityGraph = em.createEntityGraph(Claim.class);
        entityGraph.addAttributeNodes("insuredPerson", "proposal");
        entityGraph.addSubgraph("insuredPerson").addAttributeNodes("insuranceCard");
        entityGraph.addSubgraph("insuredPerson").addSubgraph("insuranceCard").addAttributeNodes("policyOwner");
        entityGraph.addSubgraph("proposal").addAttributeNodes("insuranceManager", "insuranceSurveyor");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Claim> getAllNew() {
        TypedQuery<Claim> query = em.createQuery("from Claim c where c.status = :status", Claim.class);
        query.setParameter("status", ClaimStatus.NEW);
        return query.getResultList();
    }

    @Override
    public List<Claim> getAllProcessing() {
        TypedQuery<Claim> query = em.createQuery("from Claim c where c.status = :status", Claim.class);
        query.setParameter("status", ClaimStatus.PROCESSING);
        return query.getResultList();
    }

    @Override
    public List<Claim> getAllDone() {
        TypedQuery<Claim> query = em.createQuery("from Claim c where c.status = :status", Claim.class);
        query.setParameter("status", ClaimStatus.DONE);
        return query.getResultList();
    }

    @Override
    public List<Claim> getAllProcessingAndDone() {
        List<Claim> claimList = getAll();
        List<Claim> filteredClaims = new ArrayList<>();

        for (Claim claim : claimList) {
            if (claim.getStatus().equals(ClaimStatus.PROCESSING) || claim.getStatus().equals(ClaimStatus.DONE)) {
                filteredClaims.add(claim);
            }
        }

        return filteredClaims;
    }


    @Override
    public void update(Claim claim) {
        Claim claimToUpdate = findByID(claim.getId());

        em.getTransaction().begin();
        claimToUpdate.setId(claim.getId());
        claimToUpdate.setInsuredPerson(claim.getEntireInsuredPerson());
        claimToUpdate.setCardNumber(claim.getCardNumber());
        claimToUpdate.setClaimDate(claim.getClaimDate());
        claimToUpdate.setExamDate(claim.getExamDate());
        claimToUpdate.setClaimAmount(claim.getClaimAmount());
        claimToUpdate.setStatus(claim.getStatus());
        claimToUpdate.setBankingInfo(claim.getBankingInfo());

        em.getTransaction().commit();
    }

    @Override
    public Claim removeByID(String id) {
        em.getTransaction().begin();
        Claim claimToRemove = em.find(Claim.class, id);
        em.remove(claimToRemove);
        em.getTransaction().commit();
        return claimToRemove;
    }

    @Override
    public List<Claim> getClaimsOfDependant(Dependant dependant) {
        TypedQuery<Claim> query = em.createQuery("FROM Claim WHERE insuredPerson = :dependant", Claim.class);
        query.setParameter("dependant", dependant);
        return query.getResultList();
    }

    @Override
    public List<Claim> getClaimsOfPolicyHolder(PolicyHolder policyHolder) {
        TypedQuery<Claim> query = em.createQuery("FROM Claim WHERE insuredPerson = :policyHolder", Claim.class);
        query.setParameter("policyHolder", policyHolder);
        return query.getResultList();
    }

    @Override
    public List<Claim> getClaimsOfPolicyHolderAndTheirDependants(PolicyHolder policyHolder) {
        Set<Beneficiary> dependents = new HashSet<>(policyHolder.getDependantSet());

        dependents.add(policyHolder);

        TypedQuery<Claim> query = em.createQuery(
                "FROM Claim c WHERE c.insuredPerson IN :insuredPersons", Claim.class);
        query.setParameter("insuredPersons", dependents);

        return query.getResultList();
    }
}
