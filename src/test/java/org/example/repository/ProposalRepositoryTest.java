package org.example.repository;

import com.example.finalassingment.model.items.Proposal;
import com.example.finalassingment.repository.impl.ProposalRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProposalRepositoryTest {

    @Test
    void findByID() {
        ProposalRepository repository = new ProposalRepository();
        assertNotNull(repository.findByID(1));
        repository.close();
    }

    @Test
    void getAll() {
        ProposalRepository repository = new ProposalRepository();
        List<Proposal> proposalList = repository.getAll();
        assertNotNull(proposalList);
        repository.close();
    }
}