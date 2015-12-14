package com.jpanchenko.chat.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Julia on 29.11.2015.
 */
@Entity
@Table(name = "users", schema = "chat")
public class User extends BaseEntity {
    private int id;
    private String password;
    private String firstname;
    private String lastname;
    private Date birtdate;
    private boolean enabled;
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "password", length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "firstname", length = 100, nullable = false)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", length = 100, nullable = false)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "birtdate")
    public Date getBirtdate() {
        return birtdate;
    }

    public void setBirtdate(Date birtdate) {
        this.birtdate = birtdate;
    }

    @Basic
    @Column(name = "enabled", nullable = false, columnDefinition = "BIT")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "email", length = 100, nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (enabled != user.enabled) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
        if (birtdate != null ? !birtdate.equals(user.birtdate) : user.birtdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (birtdate != null ? birtdate.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        User user;

        public Builder() {
            user = new User();
            user.setEnabled(true);
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder firstname(String firstName) {
            user.firstname = firstName;
            return this;
        }

        public Builder lastname(String lastName) {
            user.lastname = lastName;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder birtdate(Date birtdate) {
            user.birtdate = birtdate;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
