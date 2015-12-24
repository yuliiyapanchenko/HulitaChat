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
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/public/chat")
public class PublicChatController {

    private final ChatRepository chatRepository;
    private final List<DeferredResult<List<String>>> chatRequests = new CopyOnWriteArrayList<>();

    @Autowired
    public PublicChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postMessage(@RequestParam String message) {
        String userName = SecurityUtil.getCurrentUserFullName();
        message = "[" + userName + "] " + message;
        this.chatRepository.addMessage(message);
        for (DeferredResult<List<String>> entry : this.chatRequests) {
            List<String> messages = this.chatRepository.getLastMessage();
            entry.setResult(messages);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<List<String>> getMessages(@RequestParam int messageIndex) {
        final DeferredResult<List<String>> deferredResult = new DeferredResult<>(null, Collections.emptyList());
        this.chatRequests.add(deferredResult);
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


}
