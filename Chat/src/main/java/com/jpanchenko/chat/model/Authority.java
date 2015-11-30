package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by Julia on 29.11.2015.
 */
@Entity
@Table(name = "authorities", schema = "chat")
public class Authority {
    private int id;
    private User user;
    private Role role;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        if (id != authority.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
