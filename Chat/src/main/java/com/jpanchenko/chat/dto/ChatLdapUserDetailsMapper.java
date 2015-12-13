package com.jpanchenko.chat.dto;

import com.jpanchenko.chat.exception.DuplicateEmailException;
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
            ChatUserDetails chatUserDetails = new ChatUserDetails.Builder()
                    .firstname(ctx.getStringAttribute("cn"))
                    .lastName(ctx.getStringAttribute("sn"))
                    .password(user.getPassword())
                    .username(user.getUsername())
                    .authorities(authorities)
                    .build();
            User newUser;
            try {
                newUser = userService.addUser(chatUserDetails);
            } catch (DuplicateEmailException e) {
                return chatUserDetails;
            }
            chatUserDetails.setId(newUser.getId());
            chatUserDetails.setAuthorityList(newUser.getAuthorities());
            return chatUserDetails;
        }
    }
}
