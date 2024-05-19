package com.example.finalassingment.model.items;
/**
 * @author Group 11
 */
import jakarta.persistence.*;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.provider.InsuranceSurveyor;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private InsuranceSurveyor insuranceSurveyor;
    @ManyToOne
    private Beneficiary beneficiary;
    @OneToOne
    private Claim claim;
    private String message;

    public Request(InsuranceSurveyor insuranceSurveyor, Beneficiary beneficiary, Claim claim, String message) {
        this.insuranceSurveyor = insuranceSurveyor;
        this.beneficiary = beneficiary;
        this.claim = claim;
        this.message = message;
    }

    public Request() {

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

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Claim getClaim() {
        return claim;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", insuranceSurveyor=" + insuranceSurveyor +
                ", beneficiary=" + beneficiary +
                ", message='" + message + '\'' +
                '}';
    }
}
