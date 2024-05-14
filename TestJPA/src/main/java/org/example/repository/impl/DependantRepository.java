//package org.example.repository.impl;
//
//import jakarta.persistence.TypedQuery;
//import org.example.model.customer.Customer;
//import org.example.model.customer.Dependant;
//import org.example.model.customer.PolicyHolder;
//import org.example.model.customer.PolicyOwner;
//import org.example.repository.EntityRepository;
//import org.example.repository.IDependantRepository;
//
//import java.util.List;
//
//public class DependantRepository extends EntityRepository implements IDependantRepository {
//    @Override
//    public void add(Dependant dependant) {
//        em.getTransaction().begin();
//        em.persist(dependant);
//        em.getTransaction().commit();
//    }
//
//    @Override
//    public void add(Dependant... dependants) {
//        em.getTransaction().begin();
//        for (Dependant c : dependants) {
//            em.persist(c);
//        }
//        em.getTransaction().commit();
//    }
//
//    @Override
//    public Dependant findByID(int id) {
//        return em.find(Dependant.class, id);
//    }
//
//    @Override
//    public List<Dependant> getAll() {
//        TypedQuery<Dependant> query = em.createQuery("from Dependant ", Dependant.class);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Dependant> getAllOfPolicyHolder(PolicyHolder policyHolder) {
//        TypedQuery<Dependant> query = em.createQuery("from Dependant d where d.policyHolder = :policyHolder", Dependant.class);
//        query.setParameter("policyHolder", policyHolder);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Dependant> getAllOfPolicyOwner(PolicyOwner policyOwner) {
//        TypedQuery<Dependant> query = em.createQuery("from Dependant d where d.policyOwner = :policyOwner", Dependant.class);
//        query.setParameter("policyOwner", policyOwner);
//        return query.getResultList();
//    }
//
//    @Override
//    public void update(Dependant dependant) {
//        Dependant dependantToUpdate = findByID(dependant.getId());
//        dependantToUpdate.setAddress(dependant.getAddress());
//        dependantToUpdate.setEmail(dependant.getEmail());
//        dependantToUpdate.setPhone(dependant.getPhone());
//        dependantToUpdate.setPassword(dependant.getPassword());
//    }
//
//    @Override
//    public Dependant removeByID(int id) {
//        em.getTransaction().begin();
//        Dependant dependantToRemove = findByID(id);
//        em.remove(dependantToRemove);
//        em.getTransaction().commit();
//        return dependantToRemove;
//    }
//}
