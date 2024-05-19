package com.example.finalassingment.model.items;

import jakarta.persistence.*;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.customer.PolicyOwner;

import java.time.LocalDate;

@Entity
public class InsuranceCard {
    @Id
    private String cardNumber;
    @OneToOne
    private Beneficiary cardHolder;
    @ManyToOne
    @JoinColumn(name = "policyOwner_id")
    private PolicyOwner policyOwner;
    @Temporal(TemporalType.DATE)
    private LocalDate expireDate;
    public InsuranceCard(InsuranceCardBuilder cardBuilder) {
        this.cardNumber = cardBuilder.cardNumber;
        this.cardHolder = cardBuilder.cardHolder;
        this.policyOwner = cardBuilder.policyOwner;
        this.expireDate = cardBuilder.expireDate;

        cardHolder.setInsuranceCard(this);
    }
    public InsuranceCard() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Beneficiary getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Beneficiary cardHolder) {
        this.cardHolder = cardHolder;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "InsuranceCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolder=" + cardHolder.getId() +
                ", policyOwner=" + (policyOwner != null ? policyOwner.getId() : "None") +
                ", expireDate=" + expireDate +
                '}';
    }


    public static class InsuranceCardBuilder {
        protected String cardNumber;
        protected Beneficiary cardHolder;
        protected PolicyOwner policyOwner;
        protected LocalDate expireDate;

        public InsuranceCardBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public InsuranceCardBuilder cardHolder(Beneficiary cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public InsuranceCardBuilder policyOwner(PolicyOwner policyOwner) {
            this.policyOwner = policyOwner;
            return this;
        }

        public InsuranceCardBuilder expireDate(LocalDate expiryDate) {
            this.expireDate = expiryDate;
            return this;
        }

        public InsuranceCard build() {
            return new InsuranceCard(this);
        }


    }
}
