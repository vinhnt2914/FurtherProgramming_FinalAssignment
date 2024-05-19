package com.example.finalassingment.service;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.items.InsuranceCard;

public class InsuranceCardService {
    public InsuranceCard.InsuranceCardBuilder makeCard() {
        return new InsuranceCard.InsuranceCardBuilder();
    }
}
