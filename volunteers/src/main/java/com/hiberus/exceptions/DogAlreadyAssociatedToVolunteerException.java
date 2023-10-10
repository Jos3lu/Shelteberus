package com.hiberus.exceptions;

public class DogAlreadyAssociatedToVolunteerException extends Exception {
    public DogAlreadyAssociatedToVolunteerException() {
        super("Dog is already associated to volunteer");
    }
}
