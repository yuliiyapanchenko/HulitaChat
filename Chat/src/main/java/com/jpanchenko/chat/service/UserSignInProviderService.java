package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.model.UserSigInProvider;
import com.jpanchenko.chat.repository.UserSignInProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Julia on 05.12.2015.
 */
@Service
public class UserSignInProviderService {

    @Autowired
    UserSignInProviderRepository repository;

    public UserSigInProvider getUserSignInProvider(User user) {
        return repository.getUserSignInProvider(user);
    }

    public void addUserSigInProvider(UserSigInProvider sigInProvider) {
        repository.addUserSigInProvider(sigInProvider);
    }
}
