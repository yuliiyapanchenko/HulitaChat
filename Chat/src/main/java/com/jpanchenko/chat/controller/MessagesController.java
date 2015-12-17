package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.service.MessageService;
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
    private MessageService messageService;
    
    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public List<MessageDto> getNewMessages() {
        return messageService.getNewMessages();
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public void postMessage(@RequestParam int idConversation, @RequestParam String message) {
        messageService.postMessage(idConversation, message);
    }

    @RequestMapping(path = "/read", method = RequestMethod.POST)
    public void readMessage(@RequestParam int idMessage) {
        messageService.markMessageAsRead(idMessage);
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public List<MessageDto> getMessages(@RequestParam int idConversation,
                                                    @RequestParam(required = false, defaultValue = "0") int start,
                                                    @RequestParam(required = false, defaultValue = "20") int end) {
        return messageService.getMessages(idConversation, start, end);
    }
}
