package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.ConversationDto;
import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UsersConversations;
import com.jpanchenko.chat.service.ConversationService;
import com.jpanchenko.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by jpanchenko on 15.12.2015.
 */
@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ConversationDto addConversation(@RequestParam(required = false) String title,
                                           @RequestBody Collection<UserDto> contacts) {
        if (title == null || title.isEmpty() || title.trim().isEmpty()) {
            for (UserDto contact : contacts)
                title += contact.getFirstname() + " " + contact.getLastname() + ";";
        }
        User currUser = userService.getLoggedInUser();
        Conversation conversation = createConversation(title, currUser);
        addUserConversation(conversation, currUser);
        for (UserDto contact : contacts){
            addUserConversation(conversation, userService.getUserById(contact.getId()));
        }
        return null;
    }

    private Conversation createConversation(String title, User currUser) {
        Conversation conversation = new Conversation();
        conversation.setCreator(currUser);
        conversation.setAdmin(currUser);
        conversation.setTitle(title);
        conversationService.addConversation(conversation);
        return conversation;
    }

    private void addUserConversation(Conversation conversation, User currUser) {
        UsersConversations usersConversations = new UsersConversations();
        usersConversations.setUser(currUser);
        usersConversations.setConversation(conversation);
        conversationService.addUserConversation(usersConversations);
    }
}
