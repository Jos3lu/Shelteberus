package com.hiberus.exceptions;

public class DogNotFoundException extends Exception {
    public DogNotFoundException(Long dogId) {
        super("Dog not found: " + dogId);
    }
}
