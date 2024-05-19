package com.example.finalassingment.repository.impl;

import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.items.Request;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IRequestRepository;

import java.util.List;

public class RequestRepository extends EntityRepository implements IRequestRepository {
    @Override
    public void add(Request request) {
        em.getTransaction().begin();
        em.persist(request);
        em.getTransaction().commit();
    }

    @Override
    public void add(Request... requests) {
        em.getTransaction().begin();
        for (Request r : requests) {
            em.persist(r);
        }
        em.getTransaction().commit();
    }

    @Override
    public Request findByID(int id) {
        return em.find(Request.class, id);
    }

    @Override
    public List<Request> getAll() {
        TypedQuery<Request> query = em.createQuery(
                "from Request r " +
                        "join fetch r.insuranceSurveyor s " +
                        "join fetch r.beneficiary b " +
                        "join fetch b.insuranceCard ic " +
                        "join fetch ic.policyOwner " +
                        "join fetch s.manager " +
                        "join fetch r.claim c " +
                        "join fetch c.proposal ",
                Request.class);
        return query.getResultList();
    }

    @Override
    public List<Request> getAllToBeneficiary(Beneficiary beneficiary) {
        TypedQuery<Request> query = em.createQuery("from Request r where r.beneficiary = :beneficiary", Request.class);
        query.setParameter("beneficiary", beneficiary);
        return query.getResultList();
    }

    @Override
    public Request removeByID(int id) {
        em.getTransaction().begin();

        Request requestToRemove = em.find(Request.class, id);
        em.remove(requestToRemove);

        em.getTransaction().commit();
        return requestToRemove;
    }
}
