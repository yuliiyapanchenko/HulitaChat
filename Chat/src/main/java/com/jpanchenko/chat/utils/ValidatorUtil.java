package com.jpanchenko.chat.utils;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * Created by Julia on 05.12.2015.
 */
public class ValidatorUtil {

    public static void addValidationError(String field, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(field)
                .addConstraintViolation();
    }

    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }
}
