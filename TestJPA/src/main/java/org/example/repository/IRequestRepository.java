package org.example.repository;

import org.example.model.customer.Beneficiary;
import org.example.model.customer.Customer;
import org.example.model.items.Request;

import java.util.List;

public interface IRequestRepository {
    void add(Request request);
    void add(Request... requests);
    Request findByID(int id);
    List<Request> getAll();
    List<Request> getAllTo(Beneficiary customer);
    Request removeByID(int id);
    void close();
}
