package org.example.repository;

import org.example.model.items.InsuranceCard;
import org.example.model.items.Request;

import java.util.List;

public interface IRequestRepository {
    void add(Request request);
    void add(Request... requests);
    Request findByID(Long id);
    List<Request> getAll();
    InsuranceCard removeByID(String id);
    void close();
}
