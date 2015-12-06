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

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "sign_in_provider_id", referencedColumnName = "id", insertable = false, updatable = false)
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
