package org.example.repository;

import org.example.model.provider.InsuranceSurveyor;
import org.example.model.provider.Provider;

import java.util.List;

public interface IProviderRepository {
    void add(Provider provider);
    void add(Provider... providers);
    Provider findByID(int id);
    List<Provider> getAll();
    Provider removeByID(int id);
}
