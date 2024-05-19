package com.example.finalassingment.repository;

import com.example.finalassingment.model.provider.InsuranceSurveyor;

import java.util.List;

public interface IInsuranceSurveyorRepository {
    void add(InsuranceSurveyor insuranceSurveyor);
    void add(InsuranceSurveyor... insuranceSurveyors);
    InsuranceSurveyor findByID(int id);
    List<InsuranceSurveyor> getAll();
    InsuranceSurveyor removeByID(int id);
}
