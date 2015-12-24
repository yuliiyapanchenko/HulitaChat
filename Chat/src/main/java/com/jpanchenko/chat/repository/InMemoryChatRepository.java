package com.jpanchenko.chat.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryChatRepository implements ChatRepository {

    private final List<String> messages = new CopyOnWriteArrayList<>();

    public List<String> getMessages(int index) {
        if (this.messages.isEmpty()) {
            return Collections.emptyList();
        }
        Assert.isTrue((index >= 0) && (index <= this.messages.size()), "Invalid message index " + index);
        return this.messages.subList(index, this.messages.size());
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public List<String> getLastMessage() {
        return this.messages.subList(this.messages.size()-1, this.messages.size());
    }

}
