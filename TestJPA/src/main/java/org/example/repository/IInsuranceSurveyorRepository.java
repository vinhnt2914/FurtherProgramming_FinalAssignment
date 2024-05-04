package org.example.repository;

import org.example.model.items.InsuranceCard;
import org.example.model.items.Request;
import org.example.model.provider.InsuranceSurveyor;

import java.util.List;

public interface IInsuranceSurveyorRepository {
    void add(InsuranceSurveyor insuranceSurveyor);
    void add(InsuranceSurveyor... insuranceSurveyors);
    InsuranceSurveyor findByID(int id);
    List<InsuranceSurveyor> getAll();
    InsuranceSurveyor removeByID(int id);
    void close();
}
