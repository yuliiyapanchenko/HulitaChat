package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.UsersConversations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by jpanchenko on 15.12.2015.
 */
@Repository
public class ConversationRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public void addConversation(Conversation conversation) {
        entityManager.persist(conversation);
    }

    @Transactional
    public void addUserConversation(UsersConversations usersConversations) {
        entityManager.persist(usersConversations);
    }
}
