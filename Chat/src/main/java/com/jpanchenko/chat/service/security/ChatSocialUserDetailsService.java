package com.jpanchenko.chat.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Created by Julia on 04.12.2015.
 */
public class ChatSocialUserDetailsService implements SocialUserDetailsService {

    @Qualifier("userService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        return (SocialUserDetails) userDetailsService.loadUserByUsername(userId);
    }
}
