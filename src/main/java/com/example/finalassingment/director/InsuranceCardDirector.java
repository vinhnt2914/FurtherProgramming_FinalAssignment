package com.example.finalassingment.director;

import com.example.finalassingment.model.items.InsuranceCard;

public class InsuranceCardDirector {
    public InsuranceCard.InsuranceCardBuilder makeCard() {
        return new InsuranceCard.InsuranceCardBuilder();
    }
}
