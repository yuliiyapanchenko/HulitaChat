package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.Role;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.AuthorityRepository;
import com.jpanchenko.chat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Julia on 21.11.2015.
 */
@Service
public class AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void addUserAuthority(User dbUser) {
        Authority authority = new Authority();
        authority.setUser(dbUser);
        Role role = roleRepository.getRoleByName("ROLE_USER");
        authority.setRole(role);
        authorityRepository.addAuthority(authority);
    }

    public List<Authority> getUserAuthority(User user) {
        return authorityRepository.getUserAuthority(user);
    }
}
