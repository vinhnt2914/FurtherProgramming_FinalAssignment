package org.example.test;

import org.example.model.provider.Provider;
import org.example.repository.impl.ProviderRepository;

import javax.sound.sampled.Port;
import java.util.List;

public class TestProviderAttributes {
    public static void main(String[] args) {
        ProviderRepository repository = new ProviderRepository();
        List<Provider> providerList = repository.getAll();
        providerList.forEach(System.out::println);
        repository.close();
    }
}
