package com.jpanchenko.chat.validation;

import com.jpanchenko.chat.dto.security.RegistrationForm;
import com.jpanchenko.chat.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Julia on 05.12.2015.
 */
public class PasswordsNotEmptyValidator implements ConstraintValidator<PasswordsNotEmpty, RegistrationForm> {

    private String validationTriggerFieldName;
    private String passwordFieldName;
    private String passwordVerificationFieldName;

    @Override
    public void initialize(PasswordsNotEmpty passwordsNotEmpty) {
        validationTriggerFieldName = passwordsNotEmpty.triggerFieldName();
        passwordFieldName = passwordsNotEmpty.passwordFieldName();
        passwordVerificationFieldName = passwordsNotEmpty.passwordVerificationFieldName();
    }

    @Override
    public boolean isValid(RegistrationForm registrationForm, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            Object validationTrigger = ValidatorUtil.getFieldValue(registrationForm, validationTriggerFieldName);
            return validationTrigger != null || passwordsAreValid(registrationForm, context);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during validation", e);
        }
    }

    private boolean passwordsAreValid(Object value, ConstraintValidatorContext context) throws NoSuchFieldException, IllegalAccessException {
        String password = (String) ValidatorUtil.getFieldValue(value, passwordFieldName);
        if (isNullOrEmpty(password)) {
            ValidatorUtil.addValidationError(passwordFieldName, context);
            return false;
        }

        String passwordVerification = (String) ValidatorUtil.getFieldValue(value, passwordVerificationFieldName);
        if (isNullOrEmpty(passwordVerification)) {
            ValidatorUtil.addValidationError(passwordVerificationFieldName, context);
            return false;
        }
        return true;
    }

    private boolean isNullOrEmpty(String field) {
        return field == null || field.trim().isEmpty();
    }
}
