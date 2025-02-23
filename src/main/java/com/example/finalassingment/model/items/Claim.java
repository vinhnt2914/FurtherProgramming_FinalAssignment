package com.example.finalassingment.model.items;
/**
 * @author Group 11
 */
import jakarta.persistence.*;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.enums.ClaimStatus;

import java.time.LocalDate;

@Entity
public class Claim {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
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
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "claim")
    private Proposal proposal;

    public Claim(ClaimBuilder claimBuilder) {
        this.id = claimBuilder.id;
        this.insuredPerson = claimBuilder.insuredPerson;
        this.cardNumber = insuredPerson.getInsuranceCard().getCardNumber();
        this.claimDate = claimBuilder.claimDate;
        this.examDate = claimBuilder.examDate;
        this.claimAmount = claimBuilder.claimAmount;
        this.status = claimBuilder.status;
        this.bankingInfo = claimBuilder.bankingInfo;
        insuredPerson.addClaim(this);
    }

    public Claim() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInsuredPerson() {
        return insuredPerson.getId();
    }
    public Beneficiary getEntireInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Beneficiary insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Claim ID: ").append(id).append("\n")
                .append("Insured Person: ").append(insuredPerson.getId()).append("\n")
                .append("Card Number: ").append(cardNumber).append("\n")
                .append("Claim Date: ").append(claimDate).append("\n")
                .append("Exam Date: ").append(examDate).append("\n")
                .append("Claim Amount: ").append(claimAmount).append("\n")
                .append("Status: ").append(status).append("\n")
                .append("Banking Info: ").append(bankingInfo).append("\n");
        return sb.toString();
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
