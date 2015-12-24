package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UsersConversations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Transactional
    public Conversation getConversationById(int idConversation) {
        return entityManager.find(Conversation.class, idConversation);
    }

    @Transactional
    public List<Conversation> getUserLatestConversations(User user, int start, int end) {
        Iterator iterator = entityManager.createQuery("select m.conversation, max (m.creationTime) as created_time " +
                "from Message as m, UsersConversations as uc " +
                "where uc.conversation.id = m.conversation.id and uc.user.id =:userId " +
                "group by m.conversation.id " +
                "order by created_time desc ")
                .setParameter("userId", user.getId())
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList()
                .iterator();

        List<Conversation> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Object[] row = (Object[]) iterator.next();
            Conversation conversation = (Conversation) row[0];
            result.add(conversation);
        }
        return result;
    }

    @Transactional
    public int getUnreadConversationMessages(Conversation conversation, User user) {
        try {
            Long result = (Long) entityManager.createQuery("select count(*) from NewMessage where conversation =:conversation and user =:user")
                    .setParameter("conversation", conversation)
                    .setParameter("user", user)
                    .getSingleResult();
            return result.intValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    @Transactional
    public List<UserDto> getConversationUserDtos(Conversation conversation) {
        return entityManager.createQuery("select new com.jpanchenko.chat.dto.UserDto(uc.user.id, uc.user.firstname, uc.user.lastname) " +
                "from UsersConversations as uc " +
                "where uc.conversation =:conversation", UserDto.class)
                .setParameter("conversation", conversation)
                .getResultList();
    }

    @Transactional
    public List<User> getConversationUsers(Conversation conversation) {
        return entityManager.createQuery("select uc.user " +
                "from UsersConversations as uc " +
                "where uc.conversation =:conversation", User.class)
                .setParameter("conversation", conversation)
                .getResultList();
    }

    @Transactional
    public UsersConversations getUserConversation(User user, Conversation conversation) {
        try {
            return entityManager.createQuery("from UsersConversations as uc where uc.user =:user and conversation =:conversation",
                    UsersConversations.class)
                    .setParameter("user", user)
                    .setParameter("conversation", conversation)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
}
