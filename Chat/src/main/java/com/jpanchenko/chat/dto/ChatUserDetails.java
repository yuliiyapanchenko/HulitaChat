package com.jpanchenko.chat.dto;

import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.SocialMediaService;
import com.jpanchenko.chat.model.UserSigInProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Julia on 04.12.2015.
 */
public class ChatUserDetails extends SocialUser {

    private int id;
    private String firstname;
    private String lastname;
    private List<Authority> authorityList;
    private SocialMediaService socialSignInProvider;

    public ChatUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
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

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    public SocialMediaService getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public void setSocialSignInProvider(SocialMediaService socialSignInProvider) {
        this.socialSignInProvider = socialSignInProvider;
    }

    public static class Builder {
        private int id;
        private String username;
        private String firstname;
        private String lastname;
        private String password;
        private List<Authority> authorityList;
        private SocialMediaService socialSignInProvider;
        private Set<GrantedAuthority> authorities;

        public Builder() {
            this.authorities = new HashSet<>();
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder lastName(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder password(String password) {
            if (password == null) {
                password = "SocialUser";
            }
            this.password = password;
            return this;
        }

        public Builder authorityList(List<Authority> authorityList) {
            this.authorityList = authorityList;
            for (Authority auth : authorityList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth.getRole().getRole());
                this.authorities.add(authority);
            }
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities.addAll(authorities);
            return this;
        }

        public Builder socialSignInProvider(UserSigInProvider socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider == null ? null : socialSignInProvider.getSocialMediaService();
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public ChatUserDetails build() {
            ChatUserDetails user = new ChatUserDetails(username, password, authorities);
            user.id = id;
            user.firstname = firstname;
            user.lastname = lastname;
            user.authorityList = authorityList;
            user.socialSignInProvider = socialSignInProvider;
            return user;
        }
    }
}
