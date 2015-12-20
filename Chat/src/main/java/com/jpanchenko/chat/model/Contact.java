package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by jpanchenko on 14.12.2015.
 */
@Entity
@Table(name = "contacts", schema = "chat")
public class Contact extends BaseEntity {
    private int id;
    private User user;
    private User contact;

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
    @JoinColumn(name = "id_contact", referencedColumnName = "id")
    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return id == contact.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
