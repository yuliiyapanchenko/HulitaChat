package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.Role;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Julia on 21.11.2015.
 */
@Service
public class AuthorityService {

    public static final String ROLE_USER = "ROLE_USER";
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private RoleService roleService;

    public void addUserAuthority(User dbUser) {
        Authority authority = new Authority();
        authority.setUser(dbUser);
        Role role = roleService.getRoleByName(ROLE_USER);
        authority.setRole(role);
        authorityRepository.addAuthority(authority);
    }

    public List<Authority> getUserAuthority(User user) {
        return authorityRepository.getUserAuthority(user);
    }
}
