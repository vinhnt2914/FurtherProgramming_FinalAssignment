package com.example.finalassingment.repository;

import com.example.finalassingment.model.provider.InsuranceManager;

import java.util.List;

public interface IInsuranceManagerRepository {
    void add(InsuranceManager insuranceManager);
    void add(InsuranceManager... insuranceManagers);
    InsuranceManager findByID(int id);
    List<InsuranceManager> getAll();
    InsuranceManager removeByID(int id);
}
