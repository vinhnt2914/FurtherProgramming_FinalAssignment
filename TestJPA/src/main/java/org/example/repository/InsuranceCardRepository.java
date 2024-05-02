package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.InsuranceCard;

public class InsuranceCardRepository extends EntityRepository{

    public InsuranceCard find(String id) {
        return em.find(InsuranceCard.class, id);
    }
}
