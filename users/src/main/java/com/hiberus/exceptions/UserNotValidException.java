package com.hiberus.exceptions;

public class UserNotValidException extends Exception {
    public UserNotValidException() {
        super("Could not create user");
    }
}
