package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by Julia on 04.12.2015.
 */
@Entity
@Table(name = "sign_in_providers", schema = "chat")
public class SocialMediaService extends BaseEntity {
    private int id;
    private String signInProvider;

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
    @Column(name = "sign_in_provider", length = 100, nullable = false)
    public String getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(String signInProvider) {
        this.signInProvider = signInProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialMediaService that = (SocialMediaService) o;

        if (id != that.id) return false;
        if (signInProvider != null ? !signInProvider.equals(that.signInProvider) : that.signInProvider != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (signInProvider != null ? signInProvider.hashCode() : 0);
        return result;
    }
}
