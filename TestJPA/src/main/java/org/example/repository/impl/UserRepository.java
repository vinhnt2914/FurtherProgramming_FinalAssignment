package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.example.model.customer.Customer;
import org.example.repository.EntityRepository;
import org.example.repository.IUserInterface;

import java.util.List;

public class UserRepository extends EntityRepository implements IUserInterface {
    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("from User ", User.class);
        return query.getResultList();
    }

    @Override
    public User findUser(String username, String password) {
        TypedQuery<User> query = em.createQuery("from User u where u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password);
        return query.getSingleResult();
    }

    @Override
    public User findByID(int id) {
        return em.find(User.class, id);
    }
}
