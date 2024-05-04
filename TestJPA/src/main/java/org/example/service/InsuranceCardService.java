package org.example.service;

import org.example.model.items.InsuranceCard;

public class InsuranceCardService {
    public InsuranceCard.InsuranceCardBuilder makeCard() {
        return new InsuranceCard.InsuranceCardBuilder();
    }
}
