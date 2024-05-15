package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.customer.*;
import org.example.repository.EntityRepository;
import org.example.repository.ICustomerRepository;

import java.util.List;

public class CustomerRepository extends EntityRepository implements ICustomerRepository {

    @Override
    public void add(Customer customer) {
        em.getTransaction().begin();
        System.out.println("HASHED PASSWORD: " + customer.getPassword());
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
    public List<Dependant> getAllDependants() {
        TypedQuery<Dependant> query = em.createQuery("from Dependant ", Dependant.class);
        return query.getResultList();
    }

    @Override
    public List<Dependant> getAllDependantsOfPolicyHolder(PolicyHolder policyHolder) {
        TypedQuery<Dependant> query = em.createQuery("from Dependant d where d.policyHolder = :policyHolder", Dependant.class);
        query.setParameter("policyHolder", policyHolder);
        return query.getResultList();
    }

    @Override
    public List<Dependant> getAllDependantsOfPolicyOwner(PolicyOwner policyOwner) {
        TypedQuery<Dependant> query = em.createQuery("from Dependant d where d.policyOwner = :policyOwner", Dependant.class);
        query.setParameter("policyOwner", policyOwner);
        return query.getResultList();
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        TypedQuery<PolicyHolder> query = em.createQuery("from PolicyHolder ", PolicyHolder.class);
        return query.getResultList();
    }

    @Override
    public List<PolicyHolder> getAllPolicyHoldersOfPolicyOwner(PolicyOwner policyOwner) {
        TypedQuery<PolicyHolder> query = em.createQuery("from PolicyHolder h where h.policyOwner = :policyOwner", PolicyHolder.class);
        query.setParameter("policyOwner", policyOwner);
        return query.getResultList();
    }

    @Override
    public List<Beneficiary> getAllBeneficiaryOfPolicyOwner(PolicyOwner policyOwner) {
        TypedQuery<Beneficiary> query = em.createQuery("from Beneficiary b where b.policyOwner = :policyOwner", Beneficiary.class);
        query.setParameter("policyOwner", policyOwner);
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

    public PolicyHolder getPolicyHolder() {
        List<Customer> customers = getAll();
        return customers.stream()
                .filter(customer -> customer instanceof PolicyHolder)
                .map(customer -> (PolicyHolder) customer)
                .findFirst()
                .orElse(null);
    }
}
