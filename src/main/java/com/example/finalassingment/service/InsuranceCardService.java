package com.example.finalassingment.service;

import com.example.finalassingment.model.items.InsuranceCard;

public class InsuranceCardService {
    public InsuranceCard.InsuranceCardBuilder makeCard() {
        return new InsuranceCard.InsuranceCardBuilder();
    }
}
