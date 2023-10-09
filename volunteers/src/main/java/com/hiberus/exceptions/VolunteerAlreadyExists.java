package com.hiberus.exceptions;

public class VolunteerAlreadyExists extends Exception {
    public VolunteerAlreadyExists(String name) {
        super("Volunteer already exists: " + name);
    }
}
