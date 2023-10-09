package com.hiberus.exceptions;

public class AdoptionNotFoundException extends Exception {
    public AdoptionNotFoundException(Long adoptionId) {
        super("Adoption not found: " + adoptionId);
    }
}
