package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.MessageDto;
import com.jpanchenko.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jpanchenko on 16.12.2015.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    private final List<DeferredResult<List<MessageDto>>> chatRequests = new CopyOnWriteArrayList<>();

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public List<MessageDto> getNewMessages() {
        return messageService.getNewMessages();
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public void postMessage(@RequestParam int idConversation, @RequestParam String message) {
        messageService.postMessage(idConversation, message);
        for (DeferredResult<List<MessageDto>> entry : this.chatRequests) {
            List<MessageDto> messages = this.messageService.getMessages(idConversation, 1, 1);
            entry.setResult(messages);
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
        final DeferredResult<List<MessageDto>> deferredResult = new DeferredResult<>(null, Collections.emptyList());
        this.chatRequests.add(deferredResult);
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                chatRequests.remove(deferredResult);
            }
        });
        List<MessageDto> messages = messageService.getUnreadMessages(idConversation);
        if (!messages.isEmpty()) {
            deferredResult.setResult(messages);
        }
        return deferredResult;
    }
}
