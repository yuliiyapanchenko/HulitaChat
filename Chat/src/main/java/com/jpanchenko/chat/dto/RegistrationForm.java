package com.jpanchenko.chat.dto;

import com.jpanchenko.chat.model.SocialMediaService;
import com.jpanchenko.chat.validation.PasswordsNotEmpty;
import com.jpanchenko.chat.validation.PasswordsNotEqual;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by Julia on 05.12.2015.
 */

@PasswordsNotEmpty(
        triggerFieldName = "signInProvider",
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification"
)
@PasswordsNotEqual(
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification"
)
public class RegistrationForm {

    @Email
    @NotEmpty
    @Size(max = 100)
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String firstname;

    @NotEmpty
    @Size(max = 100)
    private String lastname;

    private String password;

    private String passwordVerification;

    private SocialMediaService signInProvider;

    public boolean isNormalRegistration() {
        return signInProvider == null;
    }

    public boolean isSocialSignIn() {
        return !isNormalRegistration();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }
}
