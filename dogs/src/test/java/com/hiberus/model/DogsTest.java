package com.hiberus.model;

import com.hiberus.enums.Breed;
import com.hiberus.exceptions.DogNotValidException;
import com.hiberus.models.Dog;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DogsTest {

    private static final Long ID = 1L;
    private static final String NAME = "Firulais";
    private static final Breed BREED = Breed.BEAGLE;
    private static final LocalDate BIRTH = LocalDate.now();
    private static final Long RESERVE_ID = 2L;

    @Test
    public void nameShouldNotBeNull() {
        // When
        Dog dog = new Dog(ID, null, BREED, BIRTH, RESERVE_ID);

        // Then
        assertThrows(DogNotValidException.class, dog::validDog);
    }

    @Test
    public void nameShouldNotBeEmpty() {
        // When
        Dog dog = new Dog(ID, "", BREED, BIRTH, RESERVE_ID);

        // Then
        assertThrows(DogNotValidException.class, dog::validDog);
    }

    @Test
    public void breedShouldNotBeNull() {
        // When
        Dog dog = new Dog(ID, NAME, null, BIRTH, RESERVE_ID);

        // Then
        assertThrows(DogNotValidException.class, dog::validDog);
    }

    @Test
    public void birthSouldNotBeNull() {
        // When
        Dog dog = new Dog(ID, NAME, BREED, null, RESERVE_ID);

        // Then
        assertThrows(DogNotValidException.class, dog::validDog);
    }

    @Test
    public void dogShouldNotBeReserved() {
        // When
        Dog dog = new Dog(ID, NAME, BREED, BIRTH, null);

        // Then
        assertFalse(dog::dogAlreadyReserved);
    }

    @Test
    public void dogShouldBeAlreadyReserved() {
        // When
        Dog dog = new Dog(ID, NAME, BREED, BIRTH, RESERVE_ID);

        // Then
        assertTrue(dog::dogAlreadyReserved);
    }

}
