package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.ConversationService;
import com.jpanchenko.chat.service.MessageService;
import com.jpanchenko.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private MessageService messageService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addConversation(@RequestParam(required = false) String title,
                                @RequestParam String message,
                                @RequestBody Collection<UserDto> contacts) {
        if (title == null || title.isEmpty() || title.trim().isEmpty()) {
            for (UserDto contact : contacts)
                title += contact.getFirstname() + " " + contact.getLastname() + ";";
        }
        User currUser = userService.getLoggedInUser();
        Conversation conversation = conversationService.createConversation(title, currUser);
        conversationService.addUserConversation(conversation, currUser);
        for (UserDto contact : contacts) {
            conversationService.addUserConversation(conversation, userService.getUserById(contact.getId()));
        }
        messageService.addMessage(message, currUser, conversation);
    }

}
