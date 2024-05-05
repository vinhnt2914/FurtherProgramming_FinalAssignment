package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class EntityRepository {
    protected final EntityManager em;

    public EntityRepository() {
        em = EMFactory.getInstance().createEntityManager();
    }

    public void close() {
        em.close();
    }
}
