package org.example.repository;
/**
 * @author Group 11
 */

import org.example.model.provider.InsuranceSurveyor;

import java.util.List;

public interface IInsuranceSurveyorRepository {
    void add(InsuranceSurveyor insuranceSurveyor);
    void add(InsuranceSurveyor... insuranceSurveyors);
    InsuranceSurveyor findByID(int id);
    List<InsuranceSurveyor> getAll();
    InsuranceSurveyor removeByID(int id);
}
