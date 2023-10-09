package com.hiberus.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String name) {
        super("User already exists: " + name);
    }
}
