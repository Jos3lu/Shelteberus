package com.hiberus.exceptions;

public class AdoptionAlreadyExistsException extends Exception {
    public AdoptionAlreadyExistsException() {
        super("Adoption already exists");
    }
}
