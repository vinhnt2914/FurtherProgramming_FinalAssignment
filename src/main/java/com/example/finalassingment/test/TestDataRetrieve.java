package com.example.finalassingment.test;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.provider.Provider;
import com.example.finalassingment.repository.impl.ProviderRepository;

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
