package org.example.repository;
/**
 * @author Group 11
 */

import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.model.provider.Provider;

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
