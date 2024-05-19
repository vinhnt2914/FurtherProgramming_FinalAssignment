package org.example.repository;
/**
 * @author Group 11
 */
import org.example.model.items.InsuranceCard;

import java.util.List;

public interface IInsuranceCardRepository {
    void add(InsuranceCard insuranceCard);
    void add(InsuranceCard... insuranceCards);
    InsuranceCard findByID(String id);
    List<InsuranceCard> getAll();
    void update(InsuranceCard insuranceCard);
    InsuranceCard removeByID(String id);
}
