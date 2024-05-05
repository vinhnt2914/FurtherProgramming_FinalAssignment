package org.example.model.items;

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
    @OneToOne(fetch = FetchType.LAZY)
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
}
