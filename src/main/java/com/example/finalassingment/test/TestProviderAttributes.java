package com.example.finalassingment.test;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.provider.Provider;
import com.example.finalassingment.repository.impl.ProviderRepository;

import java.util.List;

public class TestProviderAttributes {
    public static void main(String[] args) {
        ProviderRepository repository = new ProviderRepository();
        List<Provider> providerList = repository.getAll();
        providerList.forEach(System.out::println);
        repository.close();
    }
}
