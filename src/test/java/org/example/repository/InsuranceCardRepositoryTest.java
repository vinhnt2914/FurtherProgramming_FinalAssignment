package org.example.repository;

import com.example.finalassingment.model.items.InsuranceCard;
import com.example.finalassingment.repository.impl.InsuranceCardRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceCardRepositoryTest {
    @Test
    void shouldGetInsuranceCard() {
        InsuranceCardRepository repository = new InsuranceCardRepository();
        List<InsuranceCard> cardList = repository.getAll();
        assertNotNull(cardList);
        repository.close();
    }

}