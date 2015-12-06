package com.jpanchenko.chat.utils;

import com.jpanchenko.chat.dto.ChatUserDetails;
import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UserSigInProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Created by Julia on 05.12.2015.
 */
public class SecurityUtil {
    public static void logInUser(User user, List<Authority> authorities, UserSigInProvider signInProvider) {
        ChatUserDetails userDetails = new ChatUserDetails.Builder()
                .firstname(user.getFirstname())
                .lastName(user.getLastname())
                .username(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .authorityList(authorities)
                .socialSignInProvider(signInProvider)
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
