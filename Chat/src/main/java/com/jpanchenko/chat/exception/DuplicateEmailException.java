package com.jpanchenko.chat.exception;

/**
 * Created by Julia on 05.12.2015.
 */
public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
