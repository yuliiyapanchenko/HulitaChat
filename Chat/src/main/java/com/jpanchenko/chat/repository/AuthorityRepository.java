package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Julia on 21.11.2015.
 */
@Repository
public class AuthorityRepository {
    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public void addAuthority(Authority authority) {
        entityManager.persist(authority);
    }

    public List<Authority> getUserAuthority(User user) {
        return entityManager.createQuery("from Authorities where user =:user")
                .setParameter("user", user)
                .getResultList();
    }
}
