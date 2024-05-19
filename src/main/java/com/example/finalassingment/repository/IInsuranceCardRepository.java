package com.example.finalassingment.repository;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.items.InsuranceCard;

import java.util.List;

public interface IInsuranceCardRepository {
    void add(InsuranceCard insuranceCard);
    void add(InsuranceCard... insuranceCards);
    InsuranceCard findByID(String id);
    List<InsuranceCard> getAll();
    void update(InsuranceCard insuranceCard);
    InsuranceCard removeByID(String id);
}
