package org.example.service;
/**
 * @author Group 11
 */
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;

public class ProviderService {

    public InsuranceSurveyor.SurveyorBuilder makeSurveyor() {
        return new InsuranceSurveyor.SurveyorBuilder();
    }

    public InsuranceManager.ManagerBuilder makeManager() {
        return new InsuranceManager.ManagerBuilder();
    }

}
