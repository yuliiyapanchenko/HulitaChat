package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.Role;
import com.jpanchenko.chat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Julia on 05.12.2015.
 */
@Service
public class RoleService {

    @Autowired
    RoleRepository repository;

    public Role getRoleByName(String role) {
        return repository.getRoleByName(role);
    }
}
