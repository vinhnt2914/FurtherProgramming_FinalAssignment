package org.example.service;
/**
 * @author Group 11
 */
import org.example.model.items.InsuranceCard;

public class InsuranceCardService {
    public InsuranceCard.InsuranceCardBuilder makeCard() {
        return new InsuranceCard.InsuranceCardBuilder();
    }
}
