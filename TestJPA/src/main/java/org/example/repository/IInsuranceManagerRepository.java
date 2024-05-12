package org.example.repository;

import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;

import java.util.List;

public interface IInsuranceManagerRepository {
    void add(InsuranceManager insuranceManager);
    void add(InsuranceManager... insuranceManagers);
    InsuranceManager findByID(int id);
    List<InsuranceManager> getAll();
    InsuranceManager removeByID(int id);
}
