package com.hiberus.exceptions;

public class VolunteerNotValidException extends Exception {
    public VolunteerNotValidException() {
        super("Volunteer could not be created");
    }
}
