package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.items.Claim;
import org.example.repository.EntityRepository;
import org.example.repository.IClaimRepository;
import org.hibernate.Session;

import org.hibernate.Transaction;

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
        Session session = em.unwrap(org.hibernate.Session.class);
        Transaction tx = session.beginTransaction();
        try {
            int batchSize = 50; // Adjust batch size as needed
            for (int i = 0; i < claims.length; i++) {
                session.save(claims[i]);
                if (i > 0 && i % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to add claims", e);
        } finally {
            session.close();
        }
    }


    @Override
    public Claim findByID(String id) {
        return em.find(Claim.class, id);
    }

    @Override
    public List<Claim> getAll() {
        TypedQuery<Claim> query = em.createQuery("from Claim ", Claim.class);
        return query.getResultList();
    }

    @Override
    public void update(Claim claim) {
        Claim claimToUpdate = findByID(claim.getId());

        em.getTransaction().begin();

        claimToUpdate.setId(claim.getId());
        claimToUpdate.setInsuredPerson(claim.getInsuredPerson());

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
}
