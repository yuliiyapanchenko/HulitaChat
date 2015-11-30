package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.RoleRepository;
import com.jpanchenko.chat.repository.UserRepository;
import com.jpanchenko.chat.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpanchenko on 10.11.2015.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthorityService authorityService;

    private List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void addUser(User user) {
        user.setPassword(PasswordEncoder.bcrypt(user.getPassword()));
        user.setEnabled(true);
        userRepository.addUser(user);
        User dbUser = userRepository.getUserByUsername(user.getUsername());
        if (dbUser != null) {
            authorityService.addUserAuthority(dbUser);
        }
    }

    public Map<String, String> getUsersMap() {
        List<User> users = getUsers();
        Map<String, String> usersMap = new HashMap<>();
        for (User user : users) {
            String id = String.valueOf(user.getId());
            String name = user.getFirstname() + " " + user.getLastname();
            usersMap.put(id, name);
        }
        return usersMap;
    }

    public User getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
