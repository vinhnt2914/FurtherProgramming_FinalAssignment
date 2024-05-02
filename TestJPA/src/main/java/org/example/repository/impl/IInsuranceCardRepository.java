package org.example.repository.impl;

import org.example.model.InsuranceCard;
import org.example.model.customer.Customer;

import java.util.List;

public interface IInsuranceCardRepository {
    void add(InsuranceCard insuranceCard);
    void add(InsuranceCard... insuranceCards);
    InsuranceCard findByID(String id);
    List<InsuranceCard> getAll();
    void update(InsuranceCard insuranceCard);
    InsuranceCard removeByID(String id);
}
