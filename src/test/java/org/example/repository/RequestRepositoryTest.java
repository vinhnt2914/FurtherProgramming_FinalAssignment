package org.example.repository;

import org.example.model.items.Request;
import org.example.model.provider.Provider;
import org.example.repository.impl.ProviderRepository;
import org.example.repository.impl.RequestRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestRepositoryTest {

    @Test
    void findByID() {
        RequestRepository repository = new RequestRepository();
        assertNotNull(repository.findByID(10));
        repository.close();
    }

    @Test
    void getAll() {
        RequestRepository repository = new RequestRepository();
        List<Request> requestList = repository.getAll();
        assertNotNull(requestList);
        repository.close();
    }
}