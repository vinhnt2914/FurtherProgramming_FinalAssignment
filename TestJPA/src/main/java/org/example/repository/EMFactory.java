package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {
    private static EMFactory single_emFactory = null;
    private final EntityManagerFactory emFactory;
    private EMFactory() {
        this.emFactory = Persistence.createEntityManagerFactory("myPU");
    }

    public EntityManager createEntityManager() {
        return emFactory.createEntityManager();
    }

    public static synchronized EMFactory getInstance() {
        {
            if (single_emFactory == null)
                single_emFactory = new EMFactory();

            return single_emFactory;
        }
    }
}
