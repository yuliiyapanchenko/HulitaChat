package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by Julia on 29.11.2015.
 */
@Entity
@Table(name = "conversations", schema = "chat")
public class Conversation extends BaseEntity {
    private int id;
    private String title;
    private Conversation parent;
    private User creator;
    private User admin;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    public Conversation getParent() {
        return parent;
    }

    public void setParent(Conversation parent) {
        this.parent = parent;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "creator", referencedColumnName = "id")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "admin", referencedColumnName = "id")
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;

        return id == that.id && !(title != null ? !title.equals(that.title) : that.title != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
