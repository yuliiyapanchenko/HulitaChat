package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by jpanchenko on 16.12.2015.
 */
@Entity
@Table(name = "new_mesages", schema = "chat")
public class NewMessage {
    private int id;
    private User user;
    private Message message;
    private Conversation conversation;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_message", referencedColumnName = "id")
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_conversation", referencedColumnName = "id")
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewMessage newMessage = (NewMessage) o;

        if (id != newMessage.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
