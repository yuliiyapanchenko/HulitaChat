package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.model.*;
import com.jpanchenko.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jpanchenko on 16.12.2015.
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ConversationService conversationService;

    public List<MessageDto> getNewMessages() {
        User user = userService.getLoggedInUser();
        return messageRepository.getNewMessages(user);
    }

    public void addMessage(String message, User user, Conversation conversation) {
        Message msg = createMessage(message, user, conversation);
        createNewMessage(user, conversation, msg);
    }

    private void createNewMessage(User user, Conversation conversation, Message msg) {
        NewMessage newMessage = new NewMessage();
        newMessage.setConversation(conversation);
        newMessage.setUser(user);
        newMessage.setMessage(msg);
        messageRepository.saveNewMessage(newMessage);
    }

    private Message createMessage(String message, User user, Conversation conversation) {
        Message msg = new Message();
        message = "[" + user.getFirstname() + " " + user.getLastname() + "]" + message;
        msg.setMessage(message);
        msg.setUser(user);
        msg.setConversation(conversation);
        messageRepository.saveMessage(msg);
        return msg;
    }

    public void markMessageAsRead(int idMessage) {
        messageRepository.removeReadMessage(idMessage);
    }

    public List<MessageDto> getMessages(int idConversation, int start, int end) {
        if (!isAuthorized(idConversation))
            return null;
        return messageRepository.getMessages(idConversation, start, end);
    }

    private boolean isAuthorized(int idConversation) {
        User user = userService.getLoggedInUser();
        Conversation conversation = conversationService.getConversationById(idConversation);
        UsersConversations userConversation = conversationService.getUserConversation(user, conversation);
        return userConversation != null;
    }

    public void postMessage(int idConversation, String message) {
        User loggedInUser = userService.getLoggedInUser();
        Conversation conversation = conversationService.getConversationById(idConversation);
        addMessage(message, loggedInUser, conversation);
    }
}
