package org.example.repository.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.example.model.customer.Customer;
import org.example.repository.EntityRepository;
import org.example.repository.IUserInterface;
import org.example.utility.PasswordUtil;

import java.util.List;

public class UserRepository extends EntityRepository implements IUserInterface {
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
