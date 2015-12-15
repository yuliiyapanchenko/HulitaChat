package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by Julia on 29.11.2015.
 */
@Entity
@Table(name = "roles", schema = "chat")
public class Role extends BaseEntity {
    private int id;
    private String role;

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
    @Column(name = "role", length = 100, nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        return id == role1.id && !(role != null ? !role.equals(role1.role) : role1.role != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
