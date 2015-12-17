package com.jpanchenko.chat.dto;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jpanchenko on 16.12.2015.
 */
public class MessageDto {
    @XmlElement
    private int idMsg;
    @XmlElement
    private int idConversation;
    @XmlElement
    private String message;
    @XmlElement
    private DateTime publishedTime;

    public MessageDto() {
    }

    public MessageDto(int idMsg, int idConversation, String message, DateTime publishedTime) {
        this.idMsg = idMsg;
        this.idConversation = idConversation;
        this.message = message;
        this.publishedTime = publishedTime;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
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

    public DateTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(DateTime publishedTime) {
        this.publishedTime = publishedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDto that = (MessageDto) o;

        if (idMsg != that.idMsg) return false;
        if (idConversation != that.idConversation) return false;
        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        int result = idMsg;
        result = 31 * result + idConversation;
        result = 31 * result + message.hashCode();
        return result;
    }
}
