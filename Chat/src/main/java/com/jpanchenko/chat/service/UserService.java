package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.ChatUserDetails;
import com.jpanchenko.chat.dto.RegistrationForm;
import com.jpanchenko.chat.exception.DuplicateEmailException;
import com.jpanchenko.chat.model.Authority;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UserSigInProvider;
import com.jpanchenko.chat.repository.UserRepository;
import com.jpanchenko.chat.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpanchenko on 10.11.2015.
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    UserSignInProviderService userSignInProviderService;

    private List<User> getUsers() {
        return userRepository.getUsers();
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        List<Authority> authorities = authorityService.getUserAuthority(user);
        UserSigInProvider signInProvider = userSignInProviderService.getUserSignInProvider(user);
        return new ChatUserDetails.Builder()
                .firstname(user.getFirstname())
                .id(user.getId())
                .lastName(user.getLastname())
                .password(user.getPassword())
                .authorityList(authorities)
                .socialSignInProvider(signInProvider)
                .username(user.getEmail())
                .build();
    }

    public User registerNewUserAccount(RegistrationForm form) throws DuplicateEmailException {
        if (emailExists(form.getEmail()))
            throw new DuplicateEmailException("The email address: " + form.getEmail() + " is already in use.");
        String encodedPassword = encodePassword(form);
        User user = User.getBuilder()
                .email(form.getEmail())
                .firstname(form.getFirstname())
                .lastname(form.getLastname())
                .password(encodedPassword)
                .build();
        User newUser = userRepository.addUser(user);
        if (form.isSocialSignIn()) {
            UserSigInProvider sigInProvider = new UserSigInProvider();
            sigInProvider.setUser(newUser);
            sigInProvider.setSocialMediaService(form.getSignInProvider());
            userSignInProviderService.addUserSigInProvider(sigInProvider);
        } else {
            authorityService.addUserAuthority(newUser);
        }
        return newUser;
    }

    private boolean emailExists(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

    private String encodePassword(RegistrationForm form) {
        if (form.isNormalRegistration()) {
            return PasswordEncoder.bcrypt(form.getPassword());
        }
        return null;
    }
}
