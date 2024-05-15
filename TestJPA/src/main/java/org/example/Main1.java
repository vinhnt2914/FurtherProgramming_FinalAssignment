package org.example;

import jakarta.persistence.EntityManager;
import org.example.repository.EMFactory;
import org.example.service.DatabaseCleanupService;

public class Main1 {

    public static void main(String[] args) {
        EntityManager em = EMFactory.getInstance().createEntityManager();

        // Clean up the database
        DatabaseCleanupService cleanupService = new DatabaseCleanupService(em);
        cleanupService.deleteAllData();

        // Proceed with your operations (e.g., adding new data)
        // ... your existing code ...
    }
}
