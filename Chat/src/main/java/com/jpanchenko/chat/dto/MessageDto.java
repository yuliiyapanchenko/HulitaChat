package com.jpanchenko.chat.dto;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jpanchenko on 16.12.2015.
 */
public class MessageDto {
    @XmlElement
    private int id;
    @XmlElement
    private int idConversation;
    @XmlElement
    private String message;
    @XmlElement
    private Long publishedTime;

    public MessageDto() {
    }

    public MessageDto(int id, int idConversation, String message, DateTime publishedTime) {
        this.id = id;
        this.idConversation = idConversation;
        this.message = message;
        this.publishedTime = publishedTime.getMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(DateTime publishedTime) {
        this.publishedTime = publishedTime.getMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDto that = (MessageDto) o;

        if (id != that.id) return false;
        if (idConversation != that.idConversation) return false;
        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idConversation;
        result = 31 * result + message.hashCode();
        return result;
    }
}
