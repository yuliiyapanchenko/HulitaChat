package com.jpanchenko.chat.validation;

import com.jpanchenko.chat.dto.security.RegistrationForm;
import com.jpanchenko.chat.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Julia on 05.12.2015.
 */
public class PasswordsNotEqualValidator implements ConstraintValidator<PasswordsNotEqual, RegistrationForm> {

    private String passwordFieldName;
    private String passwordVerificationFieldName;

    @Override
    public void initialize(PasswordsNotEqual passwordsNotEqual) {
        passwordFieldName = passwordsNotEqual.passwordFieldName();
        passwordVerificationFieldName = passwordsNotEqual.passwordVerificationFieldName();
    }

    @Override
    public boolean isValid(RegistrationForm registrationForm, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            String password = (String) ValidatorUtil.getFieldValue(registrationForm, passwordFieldName);
            String passwordVerification = (String) ValidatorUtil.getFieldValue(registrationForm, passwordVerificationFieldName);
            if (!passwordsEqual(password, passwordVerification)) {
                ValidatorUtil.addValidationError(passwordFieldName, context);
                ValidatorUtil.addValidationError(passwordVerification, context);
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during validation", e);
        }
        return true;
    }

    private boolean passwordsEqual(String password, String passwordVerification) {
        return (password == null ? passwordVerification == null : password.equals(passwordVerification));
    }
}
