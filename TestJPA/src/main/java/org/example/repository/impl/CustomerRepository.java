package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyOwner;
import org.example.repository.EntityRepository;
import org.example.repository.ICustomerRepository;

import java.util.List;

public class CustomerRepository extends EntityRepository implements ICustomerRepository {

    @Override
    public void add(Customer customer) {
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }

    // Bulk add
    // This solve the problem where the "Child" object hasn't been saved to the database
    // By the time the "Parent" object is being persisted
    @Override
    public void add(Customer... customer) {
        em.getTransaction().begin();
        for (Customer c : customer) {
            em.persist(c);
        }
        em.getTransaction().commit();
    }

    @Override
    public Customer findByID(int id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> getAll() {
        TypedQuery<Customer> query = em.createQuery("from Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public void update(Customer customer) {
        Customer customerToUpdate = findByID(customer.getId());

        em.getTransaction().begin();

        customerToUpdate.setId(customer.getId());
        customerToUpdate.setFullName(customer.getFullName());

        em.getTransaction().commit();
    }

    public List<Dependant> getAllDependant() {
        em.getTransaction().begin();
        TypedQuery<Dependant> query = em.createQuery("from Dependant ", Dependant.class);
        return query.getResultList();
    }

    @Override
    public Customer removeByID(int id) {
        em.getTransaction().begin();
        Customer customerToRemove = em.find(Customer.class, id);

        if (customerToRemove instanceof PolicyOwner) {
            System.out.println("Removing a policy owner");
        } else {
            em.remove(customerToRemove);
        }
        em.getTransaction().commit();

        return customerToRemove;
    }

    // Close the repository
    @Override
    public void close() {
        em.close();
    }
}
