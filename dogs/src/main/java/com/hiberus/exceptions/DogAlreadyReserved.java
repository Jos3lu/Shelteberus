package com.hiberus.exceptions;

public class DogAlreadyReserved extends Exception {
    public DogAlreadyReserved() {
        super("Dog is already reserved");
    }
}
