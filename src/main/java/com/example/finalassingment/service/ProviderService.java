package com.example.finalassingment.service;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;

public class ProviderService {

    public InsuranceSurveyor.SurveyorBuilder makeSurveyor() {
        return new InsuranceSurveyor.SurveyorBuilder();
    }

    public InsuranceManager.ManagerBuilder makeManager() {
        return new InsuranceManager.ManagerBuilder();
    }

}
