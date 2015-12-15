package com.jpanchenko.chat.dto.security;

import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.Collection;

/**
 * Created by Julia on 14.12.2015.
 */
public class ChatLdapUserDetailsMapper extends LdapUserDetailsMapper {

    @Autowired
    UserService userService;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        try {
            return userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            LdapUserDetails user = (LdapUserDetails) super.mapUserFromContext(ctx, username, authorities);
            DaoUserDetails chatUserDetails = new DaoUserDetails.Builder()
                    .firstname(ctx.getStringAttribute("cn"))
                    .lastName(ctx.getStringAttribute("sn"))
                    .password(user.getPassword())
                    .username(user.getUsername())
                    .authorities(authorities)
                    .build();
            User newUser = userService.addUser(chatUserDetails);
            chatUserDetails.setId(newUser.getId());
            return chatUserDetails;
        }
    }
}
