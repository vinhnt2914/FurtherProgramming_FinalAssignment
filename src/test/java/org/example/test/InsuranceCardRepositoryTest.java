package org.example.test;

import org.example.model.items.InsuranceCard;
import org.example.repository.impl.InsuranceCardRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceCardRepositoryTest {

    @Test
    void shouldFindInsuranceCard() {
        InsuranceCardRepository repository = new InsuranceCardRepository();
        InsuranceCard foundCard = repository.findByID("0000000004");
        repository.close();
        assertNotNull(foundCard);
    }

}