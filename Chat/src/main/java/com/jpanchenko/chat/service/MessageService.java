package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.Message;
import com.jpanchenko.chat.model.NewMessage;
import com.jpanchenko.chat.model.User;
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

    public List<MessageDto> getNewMessages(User loggedInUser) {
        return messageRepository.getNewMessages(loggedInUser);
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
        msg.setMessage(message);
        msg.setUser(user);
        msg.setConversation(conversation);
        messageRepository.saveMessage(msg);
        return msg;
    }

    public void markMessageAsRead(int idMessage) {
        messageRepository.removeReadMessage(idMessage);
    }

    public List<MessageDto> getLastMessages(int idConversation, int start, int end) {
        return messageRepository.getLastMessages(idConversation, start, end);
    }
}
