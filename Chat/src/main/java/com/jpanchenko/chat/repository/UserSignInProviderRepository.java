package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UserSigInProvider;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Julia on 04.12.2015.
 */
@Repository
public class UserSignInProviderRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public UserSigInProvider getUserSignInProvider(User user) {
        return (UserSigInProvider) entityManager.createQuery("from UserSigInProvider where user =:user")
                .setParameter("user", user)
                .getSingleResult();
    }

    @Transactional
    public void addUserSigInProvider(UserSigInProvider sigInProvider) {
        entityManager.persist(sigInProvider);
    }
}
