package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.customer.PolicyHolder;
import org.example.model.items.Claim;
import org.example.repository.EntityRepository;
import org.example.repository.IClaimRepository;

import java.util.List;

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
        TypedQuery<Claim> query = em.createQuery("from Claim", Claim.class);
        return query.getResultList();
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
    public List<Claim> getClaimsByPolicyHolder(PolicyHolder policyHolder) {
        em.getTransaction().begin();
        TypedQuery<Claim> query = em.createQuery("FROM Claim WHERE insuredPerson = :policyHolder", Claim.class);
        query.setParameter("policyHolder", policyHolder);
        List<Claim> claims = query.getResultList();
        em.getTransaction().commit();
        return claims;
    }
}
