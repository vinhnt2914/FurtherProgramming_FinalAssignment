package org.example.repository;

import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.model.provider.Provider;
import org.example.repository.impl.ProviderRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProviderRepositoryTest {

    @Test
    void findByID() {
        ProviderRepository repository = new ProviderRepository();
        assertNotNull(repository.findByID(1));
        repository.close();
    }

    @Test
    void getAll() {
        ProviderRepository repository = new ProviderRepository();
        List<Provider> providerList = repository.getAll();
        assertNotNull(providerList);
        repository.close();
    }

    @Test
    void getAllSurveyor() {
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceSurveyor> providerList = repository.getAllSurveyor();
        assertNotNull(providerList);
        assertTrue(providerList.stream().allMatch(provider -> provider instanceof InsuranceSurveyor));
        repository.close();
    }

    @Test
    void getAllManager() {
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceManager> providerList = repository.getAllManager();
        assertNotNull(providerList);
        assertTrue(providerList.stream().allMatch(provider -> provider instanceof InsuranceManager));
        repository.close();
    }
}