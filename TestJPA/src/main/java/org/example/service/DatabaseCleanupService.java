package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class DatabaseCleanupService {

    private final EntityManager em;

    public DatabaseCleanupService(EntityManager em) {
        this.em = em;
    }

    public void deleteAllData() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Fetch the list of existing tables
            List<String> tables = em.createNativeQuery(
                            "SELECT table_name FROM information_schema.tables WHERE table_schema='public'")
                    .getResultList();

            // Delete data from tables if they exist
            if (tables.contains("claim")) em.createNativeQuery("DELETE FROM claim").executeUpdate();
            if (tables.contains("request")) em.createNativeQuery("DELETE FROM request").executeUpdate();
            if (tables.contains("proposal")) em.createNativeQuery("DELETE FROM proposal").executeUpdate();
            if (tables.contains("insurancecard")) em.createNativeQuery("DELETE FROM insurancecard").executeUpdate();
            if (tables.contains("dependant")) em.createNativeQuery("DELETE FROM dependant").executeUpdate();
            if (tables.contains("beneficiary")) em.createNativeQuery("DELETE FROM beneficiary").executeUpdate();
            if (tables.contains("policyholder")) em.createNativeQuery("DELETE FROM policyholder").executeUpdate();
            if (tables.contains("provider")) em.createNativeQuery("DELETE FROM provider").executeUpdate();
            if (tables.contains("policyowner")) em.createNativeQuery("DELETE FROM policyowner").executeUpdate();
            if (tables.contains("customers")) em.createNativeQuery("DELETE FROM customers").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
