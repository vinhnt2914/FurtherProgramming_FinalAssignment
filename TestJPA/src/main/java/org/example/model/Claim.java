package org.example.model;

import jakarta.persistence.*;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.Customer;
import org.example.model.enums.ClaimStatus;

import java.time.LocalDate;

@Entity
public class Claim {
    @Id
    private String id;
    @ManyToOne
    private Beneficiary insuredPerson;
    private String cardNumber;
    @Temporal(TemporalType.DATE)
    private LocalDate claimDate;
    @Temporal(TemporalType.DATE)
    private LocalDate examDate;
    private double claimAmount;
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
    private String bankingInfo;

    public Claim(ClaimBuilder claimBuilder) {
        this.id = claimBuilder.id;
        this.insuredPerson = claimBuilder.insuredPerson;
        this.cardNumber = insuredPerson.getInsuranceCard().getCardNumber();
        this.claimDate = claimBuilder.claimDate;
        this.examDate = claimBuilder.examDate;
        this.claimAmount = claimBuilder.claimAmount;
        this.status = claimBuilder.status;
        this.bankingInfo = claimBuilder.bankingInfo;
    }

    public Claim() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Beneficiary getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Beneficiary insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", insuredPerson=" + insuredPerson.getId() +
                ", cardNumber='" + cardNumber + '\'' +
                ", claimDate=" + claimDate +
                ", examDate=" + examDate +
                ", claimAmount=" + claimAmount +
                ", status=" + status +
                ", bankingInfo='" + bankingInfo + '\'' +
                '}';
    }

    public static class ClaimBuilder {
        protected String id;
        protected Beneficiary insuredPerson;
        protected String cardNumber;
        protected LocalDate claimDate;
        protected LocalDate examDate;
        protected double claimAmount;
        protected ClaimStatus status;
        protected String bankingInfo;

        public ClaimBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ClaimBuilder insuredPerson(Beneficiary insuredPerson) {
            this.insuredPerson = insuredPerson;
            return this;
        }

        public ClaimBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public ClaimBuilder claimDate(LocalDate claimDate) {
            this.claimDate = claimDate;
            return this;
        }

        public ClaimBuilder examDate(LocalDate examDate) {
            this.examDate = examDate;
            return this;
        }

        public ClaimBuilder claimAmount(double claimAmount) {
            this.claimAmount = claimAmount;
            return this;
        }

        public ClaimBuilder status(ClaimStatus status) {
            this.status = status;
            return this;
        }

        public ClaimBuilder bankingInfo(String bankingInfo) {
            this.bankingInfo = bankingInfo;
            return this;
        }

        public Claim build() {
            return new Claim(this);
        }
    }

}
