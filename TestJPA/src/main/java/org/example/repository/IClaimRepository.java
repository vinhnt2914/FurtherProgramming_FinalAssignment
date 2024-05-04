package org.example.repository;

import org.example.model.Claim;
import org.example.model.customer.Customer;

import java.util.List;

public interface IClaimRepository {
    void add(Claim claim);
    void add(Claim... claims);
    Claim findByID(String id);
    List<Claim> getAll();
    void update(Claim claim);
    Claim removeByID(String id);
    void close();
}
