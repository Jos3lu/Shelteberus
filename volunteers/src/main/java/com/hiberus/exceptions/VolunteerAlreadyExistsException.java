package com.hiberus.exceptions;

public class VolunteerAlreadyExistsException extends Exception {
    public VolunteerAlreadyExistsException(String name) {
        super("Volunteer already exists: " + name);
    }
}
