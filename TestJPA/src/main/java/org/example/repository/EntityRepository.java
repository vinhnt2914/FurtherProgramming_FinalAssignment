package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class EntityRepository {
    protected final EntityManager em;

    public EntityRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("customer_pu");
        em = emf.createEntityManager();
    }
}
