package com.jpanchenko.chat.repository;

import java.util.List;

public interface ChatRepository {

    List<String> getMessages(int messageIndex);

    void addMessage(String message);

    List<String> getLastMessage();
}
