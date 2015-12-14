package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.DaoUserDetails;
import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jpanchenko on 14.12.2015.
 */
@Service
public class DaoUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        List<Authority> authorities = authorityService.getUserAuthority(user);
        return new DaoUserDetails.Builder()
                .firstname(user.getFirstname())
                .id(user.getId())
                .lastName(user.getLastname())
                .password(user.getPassword())
                .authorityList(authorities)
                .username(user.getEmail())
                .build();
    }
}
