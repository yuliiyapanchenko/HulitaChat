package com.jpanchenko.chat.dto;

import com.jpanchenko.chat.model.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jpanchenko on 14.12.2015.
 */
public class DaoUserDetails extends User implements ChatUserDetails {

    private int id;
    private String firstname;
    private String lastname;
    private List<Authority> authorityList;


    public DaoUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
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

    public static class Builder {
        private int id;
        private String username;
        private String firstname;
        private String lastname;
        private String password;
        private List<Authority> authorityList;
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
                password = "DaoUser";
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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public DaoUserDetails build() {
            DaoUserDetails user = new DaoUserDetails(username, password, authorities);
            user.id = id;
            user.firstname = firstname;
            user.lastname = lastname;
            user.authorityList = authorityList;
            return user;
        }
    }
}
