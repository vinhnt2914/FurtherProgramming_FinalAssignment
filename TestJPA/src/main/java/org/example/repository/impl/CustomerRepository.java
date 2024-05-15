package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.customer.*;
import org.example.model.items.Claim;
import org.example.repository.EntityRepository;
import org.example.repository.ICustomerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerRepository extends EntityRepository implements ICustomerRepository {

    @Override
    public void add(Customer customer) {
        StatelessSession session = em.getEntityManagerFactory().unwrap(SessionFactory.class).openStatelessSession();
        Transaction tx = session.beginTransaction();
        try {
            session.insert(customer);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    // Bulk add
    // This solve the problem where the "Child" object hasn't been saved to the database
    // By the time the "Parent" object is being persisted
    @Override
    public void add(Customer... customers) {
        StatelessSession session = em.getEntityManagerFactory().unwrap(SessionFactory.class).openStatelessSession();
        Transaction tx = session.beginTransaction();
        try {
            int batchSize = 50; // Adjust batch size as needed
            for (int i = 0; i < customers.length; i++) {
                session.insert(customers[i]);
                if ((i + 1) % batchSize == 0) { // Commit every batchSize inserts
                    tx.commit();
                    tx = session.beginTransaction();
                }
            }
            tx.commit(); // Commit remaining inserts
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
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

    public List<Customer> getCustomers(int startPosition, int maxResults) {
        TypedQuery<Customer> query = em.createQuery("from Customer", Customer.class);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    // Update general attribute for customer object
    // Username, fullName, id are not allowed
    @Override
    public void update(Customer customer) {
        Customer customerToUpdate = findByID(customer.getId());

        em.getTransaction().begin();

        customerToUpdate.setAddress(customer.getAddress());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setPassword(customer.getPassword());

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
    public void addClaims(int customerId, Claim... claims) {
        Session session = em.unwrap(org.hibernate.Session.class);
        Transaction tx = session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, customerId);
            if (customer != null) {
                for (Claim claim : claims) {
                    claim.setInsuredPerson((Beneficiary) customer); // Associate claim with customer
                    session.save(claim);
                }
                tx.commit();
            } else {
                System.out.println("Customer with ID " + customerId + " not found.");
                tx.rollback();
            }
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
