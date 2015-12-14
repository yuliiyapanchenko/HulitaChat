package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.repository.ChatRepository;
import com.jpanchenko.chat.utils.SecurityUtil;
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

@RestController
@RequestMapping("/public/chat")
public class PublicChatController {

    private final ChatRepository chatRepository;
    private final Map<DeferredResult<List<String>>, Integer> chatRequests = new ConcurrentHashMap<>();

    @Autowired
    public PublicChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<List<String>> getMessages(@RequestParam int messageIndex) {
        final DeferredResult<List<String>> deferredResult = new DeferredResult<>(null, Collections.emptyList());
        this.chatRequests.put(deferredResult, messageIndex);
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                chatRequests.remove(deferredResult);
            }
        });
        List<String> messages = this.chatRepository.getMessages(messageIndex);
        if (!messages.isEmpty()) {
            deferredResult.setResult(messages);
        }
        return deferredResult;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postMessage(@RequestParam String message) {
        String userName = SecurityUtil.getCurrentUserFullName();
        message = "[" + userName + "] " + message;
        this.chatRepository.addMessage(message);
        for (Entry<DeferredResult<List<String>>, Integer> entry : this.chatRequests.entrySet()) {
            List<String> messages = this.chatRepository.getMessages(entry.getValue());
            entry.getKey().setResult(messages);
        }
    }
}
