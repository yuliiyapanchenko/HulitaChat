package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by jpanchenko on 10.11.2015.
 */
@Repository
public class UserRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public List<User> getUsers() {
        Query query = entityManager.createQuery("from User");
        return query.getResultList();
    }

    @Transactional
    public void addUser(User user) {
        List dbUser = entityManager.createQuery("from User where username = :username")
                .setParameter("username", user.getUsername())
                .getResultList();
        if (dbUser == null || dbUser.isEmpty())
            entityManager.persist(user);
    }

    @Transactional
    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Transactional
    public User getUserByUsername(String username) {
        return (User) entityManager.createQuery("from User where username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }
}
