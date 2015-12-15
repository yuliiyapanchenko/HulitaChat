package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.Contact;
import com.jpanchenko.chat.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by jpanchenko on 14.12.2015.
 */
@Repository
public class ContactsRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public void addContact(Contact contact) {
        entityManager.persist(contact);
    }

    @Transactional
    public List<UserDto> getUserContacts(User user) {
        return entityManager.createQuery("select new com.jpanchenko.chat.dto.UserDto(u.id, u.firstname, u.lastname)" +
                "from User as u where u.id in (select c.contact.id from Contact as c WHERE c.user =:user)", UserDto.class)
                .setParameter("user", user)
                .getResultList();
    }
}
