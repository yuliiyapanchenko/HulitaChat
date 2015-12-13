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

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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

//    @Autowired
//    private LdapTemplate ldapTemplate;

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
        }
        authorityService.addUserAuthority(newUser);
        return newUser;
    }

    public User addUser(ChatUserDetails userDetails) throws DuplicateEmailException {
        if (emailExists(userDetails.getUsername()))
            throw new DuplicateEmailException("The email address: " + userDetails.getUsername() + " is already in use.");
        String encodedPassword = encodePassword(userDetails.getPassword());
        User user = User.getBuilder()
                .email(userDetails.getUsername())
                .firstname(userDetails.getFirstname())
                .lastname(userDetails.getLastname())
                .password(encodedPassword)
                .build();
        User newUser = userRepository.addUser(user);
        authorityService.addUserAuthority(newUser);
        return newUser;
    }

    private String encodePassword(String password) {
        return PasswordEncoder.bcrypt(password);
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

    @PostConstruct
    private void init() {
        // hack for the login of facebook.
        try {
            String[] fieldsToMap = {
                    "id", "about", "age_range", "bio", "birthday", "context", "cover", "currency", "devices", "education", "email",
                    "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type",
                    "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format",
                    "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other",
                    "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "viewer_can_send_gift",
                    "website", "work"
            };

            Field field = Class.forName("org.springframework.social.facebook.api.UserOperations").
                    getDeclaredField("PROFILE_FIELDS");
            field.setAccessible(true);

            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, fieldsToMap);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<User> search(String firstName, String lastName) {
        List<User> users = new ArrayList<>();
        users.addAll(userRepository.search(firstName, lastName));
//        users.addAll(findUser(firstName+lastName));
        return users;
    }

//    public List<User> findUser(String dn) {
//        return (List<User>) ldapTemplate.lookup(dn, new PersonAttributesMapper());
//    }
//
//    private class PersonAttributesMapper implements AttributesMapper<User> {
//        public User mapFromAttributes(Attributes attrs) throws NamingException {
//            User user = new User();
//            user.setFirstname((String) attrs.get("cn").get());
//            user.setLastname((String) attrs.get("sn").get());
//            return user;
//        }
//    }
}
