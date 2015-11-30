package com.jpanchenko.chat.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Julia on 21.11.2015.
 */
public class PasswordEncoder {

    public static String bcrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
