package com.hiberus.exceptions;

public class UserWithReservationsException extends Exception {
    public UserWithReservationsException() {
        super("User with reservations");
    }
}
