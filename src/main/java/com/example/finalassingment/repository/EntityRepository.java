package com.example.finalassingment.repository;

import jakarta.persistence.EntityManager;

public abstract class EntityRepository {
    protected final EntityManager em;

    public EntityRepository() {
        em = EMFactory.getInstance().createEntityManager();
    }

    public void close() {
        em.close();
    }
}
