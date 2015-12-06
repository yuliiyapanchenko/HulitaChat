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
    public User getUserByEmail(String email) {
        return (User) entityManager.createQuery("from User where email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    @Transactional
    public User addUser(User user) {
        entityManager.persist(user);
        return getUserByEmail(user.getEmail());
    }
}
