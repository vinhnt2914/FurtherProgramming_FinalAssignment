package org.example.test;
/**
 * @author Group 11
 */
import org.example.model.provider.Provider;
import org.example.repository.impl.ProviderRepository;
import java.util.List;

public class TestProviderAttributes {
    public static void main(String[] args) {
        ProviderRepository repository = new ProviderRepository();
        List<Provider> providerList = repository.getAll();
        providerList.forEach(System.out::println);
        repository.close();
    }
}
