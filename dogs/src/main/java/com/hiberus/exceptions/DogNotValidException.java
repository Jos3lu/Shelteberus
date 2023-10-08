package com.hiberus.exceptions;

public class DogNotValidException extends Exception {
    public DogNotValidException() {
        super("Dog could not be created");
    }
}
