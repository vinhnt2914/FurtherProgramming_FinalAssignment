package org.example.model.items;
/**
 * @author Group 11
 */
import jakarta.persistence.*;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private InsuranceSurveyor insuranceSurveyor;
    @OneToOne
    private Claim claim;
    @ManyToOne
    private InsuranceManager insuranceManager;
    private String message;
    public Proposal(InsuranceSurveyor insuranceSurveyor, Claim claim, InsuranceManager insuranceManager, String message) {
        this.insuranceSurveyor = insuranceSurveyor;
        this.claim = claim;
        this.insuranceManager = insuranceManager;
        this.message = message;
    }

    public Proposal() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public void setInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyor = insuranceSurveyor;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
