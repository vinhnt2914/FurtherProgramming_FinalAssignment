package com.example.finalassingment.repository;
/**
 * @author Group 11
 */
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
