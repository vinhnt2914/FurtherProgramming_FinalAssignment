package org.example.repository.impl;

import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.example.repository.EntityRepository;
import org.example.repository.IUserInterface;

public class UserRepository extends EntityRepository implements IUserInterface {
    @Override
    public User findUser(String username, String password) {
        TypedQuery<User> customerQuery = em.createQuery("from User u where u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password);
        return customerQuery.getSingleResult();
    }

    @Override
    public User getUserByID(int id) {
        return em.find(User.class, id);
    }
}
