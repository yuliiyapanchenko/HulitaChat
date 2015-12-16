package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.dto.MessageDto;
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
        return entityManager.createQuery("select new com.jpanchenko.chat.dto.MessageDto(NewMessage.message.id, NewMessage.conversation.id, Message.message) " +
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
    public void removeReadMessage(int idMessage) {
        NewMessage message = entityManager.find(NewMessage.class, idMessage);
        entityManager.remove(message);
    }

    public List<MessageDto> getLastMessages(int idConversation, int start, int end) {
        entityManager.createQuery("")
        return null;
    }
}
