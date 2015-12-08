package com.jpanchenko.chat.model;

import javax.persistence.*;

/**
 * Created by Julia on 04.12.2015.
 */
@Entity
@Table(name = "user_sig_in_provider", schema = "chat")
public class UserSigInProvider extends BaseEntity {
    private int id;
    private User user;
    private SocialMediaService socialMediaService;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sign_in_provider_id", referencedColumnName = "id")
    public SocialMediaService getSocialMediaService() {
        return socialMediaService;
    }

    public void setSocialMediaService(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSigInProvider that = (UserSigInProvider) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
