package com.example.finalassingment.service;

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
