package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.UsersConversations;
import com.jpanchenko.chat.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jpanchenko on 15.12.2015.
 */
@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public void addConversation(Conversation conversation) {
        conversationRepository.addConversation(conversation);
    }

    public void addUserConversation(UsersConversations usersConversations) {
        conversationRepository.addUserConversation(usersConversations);
    }
}
