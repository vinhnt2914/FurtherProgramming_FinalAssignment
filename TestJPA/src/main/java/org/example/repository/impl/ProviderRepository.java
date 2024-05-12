package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.customer.Customer;
import org.example.model.customer.PolicyOwner;
import org.example.model.provider.InsuranceSurveyor;
import org.example.model.provider.Provider;
import org.example.repository.EntityRepository;
import org.example.repository.IProviderRepository;

import java.util.List;

public class ProviderRepository extends EntityRepository implements IProviderRepository {
    @Override
    public void add(Provider provider) {
        em.getTransaction().begin();
        em.persist(provider);
        em.getTransaction().commit();
    }

    @Override
    public void add(Provider... providers) {
        em.getTransaction().begin();
        for (Provider p : providers) {
            em.persist(p);
        }
        em.getTransaction().commit();
    }

    @Override
    public Provider findByID(int id) {
       return em.find(Provider.class, id);
    }

    @Override
    public List<Provider> getAll() {
        TypedQuery<Provider> query = em.createQuery("from Provider ", Provider.class);
        return query.getResultList();
    }

    @Override
    public Provider removeByID(int id) {
        em.getTransaction().begin();
        Provider providerToRemove = em.find(Provider.class, id);
        em.remove(providerToRemove);
        em.getTransaction().commit();

        return providerToRemove;
    }
}
