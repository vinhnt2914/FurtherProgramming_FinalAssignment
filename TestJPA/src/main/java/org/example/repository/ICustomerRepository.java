package org.example.repository;

import org.example.model.customer.Customer;

import java.util.List;

public interface ICustomerRepository {
    void add(Customer customer);
    void add(Customer... customers);
    Customer findByID(int id);
    List<Customer> getAll();
    void update(Customer customer);
    Customer removeByID(int id);
}
