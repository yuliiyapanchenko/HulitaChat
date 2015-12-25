package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.model.Conversation;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.ConversationService;
import com.jpanchenko.chat.service.MessageService;
import com.jpanchenko.chat.service.UserService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jpanchenko on 16.12.2015.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConversationService conversationService;

    private final Map<Pair<User, Conversation>, DeferredResult<List<MessageDto>>> chatRequests = new ConcurrentHashMap<>();

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public List<MessageDto> getNewMessages() {
        return messageService.getNewMessages();
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public void postMessage(@RequestParam int idConversation, @RequestParam String message) {
        messageService.postMessage(idConversation, message);
        for (Entry<Pair<User, Conversation>, DeferredResult<List<MessageDto>>> entry : this.chatRequests.entrySet()) {
            List<MessageDto> messages = this.messageService.getUnreadMessages(entry.getKey().getRight(), entry.getKey().getLeft());
            entry.getValue().setResult(messages);
        }
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

    @RequestMapping(path = "/getUnreadMessages", method = RequestMethod.GET)
    public DeferredResult<List<MessageDto>> getUnreadMessages(@RequestParam int idConversation) {
        final DeferredResult<List<MessageDto>> deferredResult = new DeferredResult<>((long) (30 * 1000), Collections.emptyList());
        Conversation conversation = conversationService.getConversationById(idConversation);
        User user = userService.getLoggedInUser();
        this.chatRequests.put(new ImmutablePair<>(user, conversation), deferredResult);
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                deferredResult.setResult(Collections.<MessageDto>emptyList());
            }
        });
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                chatRequests.remove(deferredResult);
            }
        });
        List<MessageDto> messages = messageService.getUnreadMessages(conversation, user);
        if (!messages.isEmpty()) {
            deferredResult.setResult(messages);
        }
        return deferredResult;
    }
}
