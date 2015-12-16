package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.ConversationService;
import com.jpanchenko.chat.service.MessageService;
import com.jpanchenko.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jpanchenko on 16.12.2015.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConversationService conversationService;

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public List<MessageDto> getNewMessages() {
        User loggedInUser = userService.getLoggedInUser();
        return messageService.getNewMessages(loggedInUser);
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public void postMessage(@RequestParam String message, @RequestParam int idConversation) {
        User loggedInUser = userService.getLoggedInUser();
        Conversation conversation = conversationService.getConversationById(idConversation);
        messageService.addMessage(message, loggedInUser, conversation);
    }

    @RequestMapping(path = "/read", method = RequestMethod.POST)
    public void readMessage(@RequestParam int idMessage){
        messageService.markMessageAsRead(idMessage);
    }

    @RequestMapping(path = "/lastMessages", method = RequestMethod.GET)
    public List<MessageDto> getLastMessages(@RequestParam int idConversation,
                                                        @RequestParam int start,
                                                        @RequestParam int end) {
        Conversation conversation = conversationService.getConversationById(idConversation);
        return messageService.getLastMessages(idConversation, start, end);
    }

}
