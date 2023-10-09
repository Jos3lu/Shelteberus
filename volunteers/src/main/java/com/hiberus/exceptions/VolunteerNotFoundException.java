package com.hiberus.exceptions;

public class VolunteerNotFoundException extends Exception {
    public VolunteerNotFoundException(Long volunteerId) {
        super("Volunteer not found: " + volunteerId);
    }
}
