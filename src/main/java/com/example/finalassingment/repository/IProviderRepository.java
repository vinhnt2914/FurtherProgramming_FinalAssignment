package com.example.finalassingment.repository;

import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.model.provider.Provider;

import java.util.List;

public interface IProviderRepository {
    void add(Provider provider);
    void add(Provider... providers);
    Provider findByID(int id);
    List<Provider> getAll();
    List<InsuranceSurveyor> getAllSurveyor();
    List<InsuranceSurveyor> getAllSurveyorOfManager(InsuranceManager manager);
    List<InsuranceManager> getAllManager();
    Provider removeByID(int id);
}
