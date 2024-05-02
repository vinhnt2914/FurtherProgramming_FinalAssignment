package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Claim;

public class ClaimRepository extends EntityRepository {
    public void add(Claim claim) {
        em.getTransaction().begin();
        em.persist(claim);
        em.getTransaction().commit();
    }

    public Claim find(String id) {
        return em.find(Claim.class, id);
    }

    public void remove(Claim claim) {
        em.getTransaction().begin();
        em.remove(claim);
        em.getTransaction().commit();
    }

    public void close() {
        em.close();
    }
}
