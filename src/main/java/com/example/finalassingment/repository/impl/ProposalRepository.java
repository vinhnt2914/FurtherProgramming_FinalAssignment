package com.example.finalassingment.repository.impl;

import com.example.finalassingment.model.provider.InsuranceManager;
import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.items.Proposal;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IProposalRepository;

import java.util.List;

public class ProposalRepository extends EntityRepository implements IProposalRepository {
    @Override
    public void add(Proposal proposal) {
        em.getTransaction().begin();
        em.persist(proposal);
        em.getTransaction().commit();
    }

    @Override
    public void add(Proposal... proposals) {
        em.getTransaction().begin();
        for (Proposal p : proposals) {
            em.persist(p);
        }
        em.getTransaction().commit();
    }

    @Override
    public Proposal findByID(int id) {
        return em.find(Proposal.class, id);
    }

    @Override
    public List<Proposal> getAll() {
        TypedQuery<Proposal> query = em.createQuery(
                "from Proposal p " +
                        "join fetch p.insuranceSurveyor " +
                        "join fetch p.insuranceManager " +
                        "join fetch p.claim ",
                Proposal.class);
        return query.getResultList();
    }

    @Override
    public List<Proposal> getAllToManager(InsuranceManager manager) {
        TypedQuery<Proposal> query = em.createQuery("from Proposal p where p.insuranceManager = :manager", Proposal.class);
        query.setParameter("manager", manager);
        return query.getResultList();
    }

    @Override
    public void update(Proposal proposal) {
        Proposal proposalToUpdate = findByID(proposal.getId());

        em.getTransaction().begin();

        proposalToUpdate.setId(proposal.getId());
        proposalToUpdate.setClaim(proposal.getClaim());
        proposalToUpdate.setInsuranceManager(proposal.getInsuranceManager());
        proposalToUpdate.setMessage(proposal.getMessage());

        em.getTransaction().commit();
    }

    @Override
    public Proposal removeByID(int id) {
        em.getTransaction().begin();
        Proposal proposalToRemove = em.find(Proposal.class, id);
        em.remove(proposalToRemove);
        em.getTransaction().commit();

        return proposalToRemove;
    }
}
