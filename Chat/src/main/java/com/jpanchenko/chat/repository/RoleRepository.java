package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Julia on 21.11.2015.
 */
@Repository
public class RoleRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public Role getRoleByName(String role) {
        try {
            return entityManager.createQuery("from Role where role =:role", Role.class)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
}
