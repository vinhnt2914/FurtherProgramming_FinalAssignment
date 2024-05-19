package com.example.finalassingment.repository.impl;

import com.example.finalassingment.model.customer.Customer;
import com.example.finalassingment.model.customer.PolicyOwner;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import com.example.finalassingment.model.User;
import com.example.finalassingment.repository.EntityRepository;
import com.example.finalassingment.repository.IUserRepository;
import com.example.finalassingment.utility.PasswordUtil;

import java.util.List;

public class UserRepository extends EntityRepository implements IUserRepository {
    public void add(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("from User ", User.class);
        return query.getResultList();
    }

    @Override
    public User findUser(String username, String password) {
        TypedQuery<User> query = em.createQuery("from User u where u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByID(int id) {
        return em.find(User.class, id);
    }

    @Override
    public void removeByID(int id) {
        em.getTransaction().begin();
        User userToRemove = em.find(User.class, id);
        em.remove(userToRemove);
        em.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        User userToUpdate = findByID(user.getId());

        em.getTransaction().begin();

        // If the password changed
        if (!user.getPassword().equals(userToUpdate.getPassword())) {
            String encryptedPassword = PasswordUtil.encrypt(user.getPassword());
            userToUpdate.setPassword(encryptedPassword);
        }

        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());

        em.getTransaction().commit();
    }
}
