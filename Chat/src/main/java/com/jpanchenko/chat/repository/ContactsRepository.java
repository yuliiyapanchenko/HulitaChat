package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.Contact;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by jpanchenko on 14.12.2015.
 */
@Repository
public class ContactsRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public boolean addContact(Contact contact) {
        try {
            entityManager.persist(contact);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
