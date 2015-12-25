package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.Message;
import com.jpanchenko.chat.model.NewMessage;
import com.jpanchenko.chat.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by jpanchenko on 16.12.2015.
 */
@Repository
public class MessageRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public List<MessageDto> getNewMessages(User loggedInUser) {
        return entityManager.createQuery("select new com.jpanchenko.chat.dto.MessageDto(" +
                        "NewMessage.message.id, NewMessage.conversation.id, Message.message, Message.creationTime) " +
                        "from NewMessage, Message " +
                        "join Message on Message.id=NewMessage.id " +
                        "where NewMessage.user =:user",
                MessageDto.class)
                .setParameter("user", loggedInUser)
                .getResultList();
    }

    @Transactional
    public void saveMessage(Message message) {
        entityManager.persist(message);
    }

    @Transactional
    public void saveNewMessage(NewMessage newMessage) {
        entityManager.persist(newMessage);
    }

    @Transactional
    public void removeReadMessage(int idMessage, User user) {
        try {
            NewMessage message = entityManager.createQuery("from NewMessage where user =:user and id =:idMessage", NewMessage.class)
                    .setParameter("user", user)
                    .setParameter("idMessage", idMessage)
                    .getSingleResult();
            if (message != null)
                entityManager.remove(message);
        } catch (Exception ignored) {
        }
    }

    @Transactional
    public List<MessageDto> getMessages(int idConversation, User user, int start, int end) {
        return entityManager.createQuery("select new com.jpanchenko.chat.dto.MessageDto(m.id, m.conversation.id, m.message, m.creationTime) " +
                "from Message as m where m.conversation.id =:idConversation and m.id not in " +
                "(select nm.message.id from NewMessage as nm where nm.conversation.id =:idConversation and nm.user =:user) " +
                "order by m.creationTime desc ", MessageDto.class)
                .setParameter("idConversation", idConversation)
                .setParameter("user", user)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }

    @Transactional
    public List<MessageDto> getUnreadMessages(Conversation conversation, User user) {
        return entityManager.createQuery("select new com.jpanchenko.chat.dto.MessageDto(m.id, m.conversation.id, m.message.message, m.creationTime) " +
                "from NewMessage as m where m.conversation =:conversation and m.user =:user " +
                "order by m.creationTime desc ", MessageDto.class)
                .setParameter("conversation", conversation)
                .setParameter("user", user)
                .getResultList();
    }
}
