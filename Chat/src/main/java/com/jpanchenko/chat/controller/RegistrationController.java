package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.security.RegistrationForm;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.AuthorityService;
import com.jpanchenko.chat.service.UserService;
import com.jpanchenko.chat.service.UserSignInProviderService;
import com.jpanchenko.chat.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Julia on 05.12.2015.
 */
@RestController
@RequestMapping(value = "user")
public class RegistrationController {

    private static final String USER_REGISTRATION_FORM = "user/registrationForm";
    private static final String USER_REGISTER = "/user/register";

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private UserSignInProviderService userSignInProviderService;

    private ProviderSignInUtils providerSignInUtils;

    @Inject
    public RegistrationController(ConnectionFactoryLocator connectionFactoryLocator,
                                  UsersConnectionRepository connectionRepository) {
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @RequestMapping(value = USER_REGISTER, method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        RegistrationForm registrationForm = userService.createRegistrationForm(connection);
        model.addAttribute("user", registrationForm);
        return USER_REGISTRATION_FORM;
    }

    @RequestMapping(value = USER_REGISTER, method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm form,
                                      BindingResult result,
                                      WebRequest request, Model model) {
        if (result.hasErrors()) {
            return USER_REGISTRATION_FORM;
        }
        User user = userService.createUserAccount(form, result);
        if (user == null) {
            model.addAttribute("error", true);
            return USER_REGISTRATION_FORM;
        }
        SecurityUtil.logInUser(user, authorityService.getUserAuthority(user), userSignInProviderService.getUserSignInProvider(user));
        providerSignInUtils.doPostSignUp(user.getEmail(), request);
        return "redirect:/";
    }
}