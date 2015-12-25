package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.ConversationDto;
import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UsersConversations;
import com.jpanchenko.chat.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpanchenko on 15.12.2015.
 */
@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    public void addConversation(Conversation conversation) {
        conversationRepository.addConversation(conversation);
    }

    public void addUserConversation(UsersConversations usersConversations) {
        conversationRepository.addUserConversation(usersConversations);
    }

    public Conversation getConversationById(int idConversation) {
        return conversationRepository.getConversationById(idConversation);
    }

    public Conversation createConversation(String title, User currUser) {
        Conversation conversation = new Conversation();
        conversation.setCreator(currUser);
        conversation.setAdmin(currUser);
        conversation.setTitle(title);
        addConversation(conversation);
        return conversation;
    }

    public void addUserConversation(Conversation conversation, User currUser) {
        UsersConversations usersConversations = new UsersConversations();
        usersConversations.setUser(currUser);
        usersConversations.setConversation(conversation);
        addUserConversation(usersConversations);
    }

    public List<ConversationDto> getUserLatestConversations(int start, int end) {
        User user = userService.getLoggedInUser();
        List<Conversation> userLatestConversations = conversationRepository.getUserLatestConversations(user, start, end);
        List<ConversationDto> result = new ArrayList<>();
        for (Conversation conversation : userLatestConversations) {
            List<UserDto> conversationUsers = conversationRepository.getConversationUserDtos(conversation);
            int unreadMessagesCount = conversationRepository.getUnreadConversationMessages(conversation, user);
            ConversationDto conversationDto = new ConversationDto(conversation.getId(), conversation.getTitle(), conversationUsers, unreadMessagesCount);
            result.add(conversationDto);
        }
        return result;
    }

    public ConversationDto addNewConversation(String title, String message, List<UserDto> contacts) {
        User user = userService.getLoggedInUser();
        title = getConversationTitle(title, contacts, user);
        Conversation conversation = createConversation(title, user);
        addUserConversation(conversation, user);
        for (UserDto contact : contacts) {
            addUserConversation(conversation, userService.getUserById(contact.getId()));
        }
        messageService.addMessage(message, user, conversation);

        return new ConversationDto(conversation.getId(), conversation.getTitle(), contacts, 0);
    }

    private String getConversationTitle(String title, List<UserDto> contacts, User user) {
        if (title == null || title.isEmpty() || title.trim().isEmpty()) {
            if (contacts.size() == 1) {
                title = "Conversation with " + user.getFirstname() + " " + user.getLastname() + "; ";
                for (UserDto contact : contacts)
                    title += contact.getFirstname() + " " + contact.getLastname() + "; ";
            } else {
                for (UserDto contact : contacts)
                    title += contact.getFirstname() + " " + contact.getLastname() + "; ";
            }
        }
        return title;
    }

    public UsersConversations getUserConversation(User user, Conversation conversation) {
        return conversationRepository.getUserConversation(user, conversation);
    }

    public List<User> getConversationUsers(Conversation conversation) {
        return conversationRepository.getConversationUsers(conversation);
    }
}
