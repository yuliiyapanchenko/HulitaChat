package com.jpanchenko.chat.dto;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jpanchenko on 14.12.2015.
 */
public class UserDto {

    @XmlElement
    private int id;
    @XmlElement
    private String firstname;
    @XmlElement
    private String lastname;

    public UserDto(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto that = (UserDto) o;

        return id == that.id && firstname.equals(that.firstname) && lastname.equals(that.lastname);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        return result;
    }
}
