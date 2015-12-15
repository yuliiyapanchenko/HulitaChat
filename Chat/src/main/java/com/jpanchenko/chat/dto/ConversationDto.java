package com.jpanchenko.chat.dto;

/**
 * Created by jpanchenko on 15.12.2015.
 */
public class ConversationDto {
    private int id;
    private String title;

    public ConversationDto(int id, String title) {
        this.id = id;
        this.title = title;
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
}
