package org.example.test;

import org.example.model.provider.Provider;
import org.example.repository.impl.ProviderRepository;

import java.util.List;

public class TestDataRetrieve {
    public static void main(String[] args) {
        ProviderRepository providerRepository = new ProviderRepository();
        List<Provider> providerList = providerRepository.getAll();
        for (Provider p : providerList) {
            System.out.println(p);
        }
        providerRepository.close();
    }
}
