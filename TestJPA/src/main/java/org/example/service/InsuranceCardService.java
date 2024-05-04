package org.example.service;

import org.example.model.InsuranceCard;

public class InsuranceCardService {
    public InsuranceCard.InsuranceCardBuilder makeCard() {
        return new InsuranceCard.InsuranceCardBuilder();
    }
}
