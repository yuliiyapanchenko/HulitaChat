package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.ConversationDto;
import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by jpanchenko on 15.12.2015.
 */
@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addConversation(@RequestParam(required = false) String title,
                                @RequestParam String message,
                                @RequestBody Collection<UserDto> contacts) {
        conversationService.addNewConversation(title, message, contacts);
    }

    @RequestMapping(path = "/latest", method = RequestMethod.GET)
    public List<ConversationDto> getLatestConversations(@RequestParam(required = false, defaultValue = "0") int start,
                                                        @RequestParam(required = false, defaultValue = "10") int end) {
        return conversationService.getUserLatestConversations(start, end);
    }
}
