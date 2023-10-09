package com.hiberus.exceptions;

public class UserNotReservedDogException extends Exception {
    public UserNotReservedDogException(Long userId, Long dogId) {
        super("User" + userId + " has not reserved dog " + dogId);
    }
}
