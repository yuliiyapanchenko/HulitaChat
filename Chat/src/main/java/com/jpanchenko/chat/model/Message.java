package com.jpanchenko.chat.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Julia on 29.11.2015.
 */
@Entity
@Table(name = "messages", schema = "chat")
public class Message {
    private int id;
    private Timestamp msgTime;
    private String message;
    private Conversation conversation;
    private User user;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "msg_time")
    public Timestamp getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Timestamp msgTime) {
        this.msgTime = msgTime;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ManyToOne
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (id != message1.id) return false;
        if (msgTime != null ? !msgTime.equals(message1.msgTime) : message1.msgTime != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (msgTime != null ? msgTime.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
