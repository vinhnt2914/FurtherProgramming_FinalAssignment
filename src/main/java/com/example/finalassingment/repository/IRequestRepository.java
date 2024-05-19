package com.example.finalassingment.repository;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.items.Request;

import java.util.List;

public interface IRequestRepository {
    void add(Request request);
    void add(Request... requests);
    Request findByID(int id);
    List<Request> getAll();
    List<Request> getAllToBeneficiary(Beneficiary customer);
    Request removeByID(int id);
    void close();
}
