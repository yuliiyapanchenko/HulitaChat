package com.jpanchenko.chat.dto;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by jpanchenko on 15.12.2015.
 */
public class ConversationDto {
    @XmlElement
    private int id;
    @XmlElement
    private String title;
    @XmlElement
    private List<UserDto> users;
    @XmlElement
    private int unreadMsgsCount;

    public ConversationDto() {
    }

    public ConversationDto(int id, String title, List<UserDto> users, int unreadMsgsCount) {
        this.id = id;
        this.title = title;
        this.users = users;
        this.unreadMsgsCount = unreadMsgsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public int getUnreadMsgsCount() {
        return unreadMsgsCount;
    }

    public void setUnreadMsgsCount(int unreadMsgsCount) {
        this.unreadMsgsCount = unreadMsgsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationDto that = (ConversationDto) o;

        if (id != that.id) return false;
        if (unreadMsgsCount != that.unreadMsgsCount) return false;
        if (!title.equals(that.title)) return false;
        return users.equals(that.users);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + users.hashCode();
        result = 31 * result + unreadMsgsCount;
        return result;
    }
}
