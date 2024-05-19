package com.example.finalassingment.repository.impl;

import com.example.finalassingment.model.customer.*;
import jakarta.persistence.TypedQuery;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.ICustomerRepository;

import java.util.ArrayList;
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
        List<Dependant> dependantList = getAllDependants();
        List<PolicyHolder> policyHolderList = getAllPolicyHolders();
        List<PolicyOwner> policyOwnerList = getAllPolicyOwners();
        List<Customer> customerList = new ArrayList<>();
        customerList.addAll(dependantList);
        customerList.addAll(policyHolderList);
        customerList.addAll(policyOwnerList);
        return customerList;
    }

    @Override
    public List<Dependant> getAllDependants() {
        TypedQuery<Dependant> query = em.createQuery(
                "SELECT d FROM Dependant d " +
                        "JOIN FETCH d.policyHolder ph " +
                        "JOIN FETCH ph.policyOwner po " +
                        "JOIN FETCH d.insuranceCard ic " +
                        "JOIN FETCH ph.insuranceCard phIC " +
                        "JOIN FETCH ic.policyOwner icPo " +
                        "JOIN FETCH phIC.policyOwner",
                Dependant.class);
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
        TypedQuery<PolicyHolder> query = em.createQuery(
                "from PolicyHolder ph " +
                        "join fetch ph.insuranceCard " +
                        "join fetch ph.policyOwner " +
                "where ph.policyOwner = :policyOwner",
                PolicyHolder.class);
        query.setParameter("policyOwner", policyOwner);
        List<PolicyHolder> policyHolderList = query.getResultList();
        List<Dependant> dependantList = new ArrayList<>();
        policyHolderList.forEach(policyHolder -> dependantList.addAll(policyHolder.getDependantSet()));
        return dependantList;
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        TypedQuery<PolicyHolder> query = em.createQuery(
                "FROM PolicyHolder ph " +
                        "join fetch ph.policyOwner " +
                        "join fetch ph.insuranceCard ic " +
                        "join fetch ic.policyOwner",
                PolicyHolder.class);
        return query.getResultList();
    }

    @Override
    public List<PolicyHolder> getAllPolicyHoldersOfPolicyOwner(PolicyOwner policyOwner) {
        TypedQuery<PolicyHolder> query = em.createQuery(
                "from PolicyHolder h " +
                        "join fetch h.insuranceCard " +
                        "join fetch h.policyOwner " +
                        "join fetch h.dependantSet ds " +
                        "join fetch ds.insuranceCard ic " +
                        "join fetch ic.policyOwner " +
                        "where h.policyOwner = :policyOwner",
                PolicyHolder.class);
        query.setParameter("policyOwner", policyOwner);
        return query.getResultList();
    }

    @Override
    public List<Beneficiary> getAllBeneficiaryOfPolicyOwner(PolicyOwner policyOwner) {
        TypedQuery<PolicyHolder> query = em.createQuery("from PolicyHolder ph where ph.policyOwner = :policyOwner", PolicyHolder.class);
        query.setParameter("policyOwner", policyOwner);
        List<PolicyHolder> policyHolders = query.getResultList();
        List<Beneficiary> res = new ArrayList<>(policyHolders);
        policyHolders.forEach(policyHolder -> res.addAll(policyHolder.getDependantSet()));
        return res;
    }

    @Override
    public List<Beneficiary> getAllPolicyHoldersAndDependants() {
        List<PolicyHolder> policyHolderList = getAllPolicyHolders();
        List<Dependant> dependantList = getAllDependants();
        List<Beneficiary> res = new ArrayList<>();
        res.addAll(policyHolderList);
        res.addAll(dependantList);
        return res;
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

    @Override
    public List<PolicyOwner> getAllPolicyOwners() {
        TypedQuery<PolicyOwner> query = em.createQuery("from PolicyOwner", PolicyOwner.class);
        return query.getResultList();
    }

}
