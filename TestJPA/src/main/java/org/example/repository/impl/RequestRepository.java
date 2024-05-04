package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.items.Request;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.EntityRepository;
import org.example.repository.IRequestRepository;

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
        TypedQuery<Request> query = em.createQuery("from Request ", Request.class);
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

    @Override
    public void close() {
        em.close();
    }
}
