package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.RegistrationForm;
import com.jpanchenko.chat.exception.DuplicateEmailException;
import com.jpanchenko.chat.model.SocialMediaService;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.AuthorityService;
import com.jpanchenko.chat.service.UserService;
import com.jpanchenko.chat.service.UserSignInProviderService;
import com.jpanchenko.chat.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Julia on 05.12.2015.
 */
@Controller
@SessionAttributes(value = "user")
public class RegistrationController {

    public static final String USER_REGISTRATION_FORM = "user/registrationForm";
    public static final String USER_REGISTER = "/user/register";
    @Autowired
    com.jpanchenko.chat.service.SocialMediaService socialMediaService;
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
        RegistrationForm registrationForm = createRegistrationForm(connection);
        model.addAttribute("user", registrationForm);
        return USER_REGISTRATION_FORM;
    }

    @RequestMapping(value = USER_REGISTER, method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm form,
                                      BindingResult result,
                                      WebRequest request) {
        if (result.hasErrors()) {
            return USER_REGISTRATION_FORM;
        }
        User user = createUserAccount(form, result);
        if (user == null) {
            return USER_REGISTRATION_FORM;
        }
        SecurityUtil.logInUser(user, authorityService.getUserAuthority(user), userSignInProviderService.getUserSignInProvider(user));
        providerSignInUtils.doPostSignUp(user.getEmail(), request);
        return "redirect:/";
    }

    private User createUserAccount(RegistrationForm form, BindingResult result) {
        try {
            return userService.registerNewUserAccount(form);
        } catch (DuplicateEmailException ex) {
            addFieldError("user", "email", form.getEmail(), "NotExist.user.email", result);
        }
        return null;
    }

    private void addFieldError(String objectName, String fieldName, String fieldValue, String errorCode, BindingResult result) {
        FieldError fieldError = new FieldError(objectName, fieldName, fieldValue, false, new String[]{errorCode}, new Object[]{}, errorCode);
        result.addError(fieldError);
    }

    private RegistrationForm createRegistrationForm(Connection connection) {
        RegistrationForm registrationForm = new RegistrationForm();
        if (connection != null) {
            UserProfile userProfile = connection.fetchUserProfile();
            registrationForm.setEmail(userProfile.getEmail());
            registrationForm.setFirstname(userProfile.getFirstName());
            registrationForm.setLastname(userProfile.getLastName());
            ConnectionKey providerKey = connection.getKey();
            String providerId = providerKey.getProviderId();
            SocialMediaService socialMediaService = this.socialMediaService.getSocialMediaService(providerId);
            registrationForm.setSignInProvider(socialMediaService);
        }
        return registrationForm;
    }
}