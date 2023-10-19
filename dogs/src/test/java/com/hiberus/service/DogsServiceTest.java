package com.hiberus.service;

import com.hiberus.dtos.UserResponseDto;
import com.hiberus.enums.Breed;
import com.hiberus.exceptions.DogAlreadyReserved;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.exceptions.DogNotValidException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.Dog;
import com.hiberus.repositories.DogsRepository;
import com.hiberus.services.UsersService;
import com.hiberus.services.impl.DogsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DogsServiceTest {

    @Mock
    private DogsRepository dogsRepository;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private DogsServiceImpl dogsService;

    @Test
    public void shouldGetDogs() {
        // When
        when(dogsRepository.findAll()).thenReturn(new ArrayList<>());

        // Then
        assertDoesNotThrow(dogsService::getDogs);
        assertEquals(dogsService.getDogs(), new ArrayList<>());
        verify(dogsRepository, times(2)).findAll();
    }

    @Test
    public void shouldGetDog() throws DogNotFoundException {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);
        Long dogId = 1L;

        // When
        when(dogsRepository.findById(anyLong())).thenReturn(Optional.of(dog));

        // Then
        assertDoesNotThrow(() -> dogsService.getDog(dogId));
        assertEquals(dogsService.getDog(dogId), dog);
        verify(dogsRepository, times(2)).findById(dogId);
    }

    @Test
    public void shouldGetUserReservedDogs() {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);
        Long userId = 2L;

        // When
        when(dogsRepository.findByReserveId(anyLong())).thenReturn(List.of(dog));

        // Then
        assertDoesNotThrow(() -> dogsService.getUserReservedDogs(userId));
        assertEquals(dogsService.getUserReservedDogs(userId), List.of(dog));
        verify(dogsRepository, times(2)).findByReserveId(userId);
    }

    @Test
    public void shouldGetVolunteerDogs() {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);
        List<Long> dogsId = List.of(1L);

        // When
        when(dogsRepository.findAllById(anyCollection())).thenReturn(List.of(dog));

        // Then
        assertDoesNotThrow(() -> dogsService.getVolunteerDogs(dogsId));
        assertEquals(dogsService.getVolunteerDogs(dogsId), List.of(dog));
        verify(dogsRepository, times(2)).findAllById(dogsId);
    }

    @Test
    public void shouldCreateDog() throws DogNotValidException {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);

        // When
        when(dogsRepository.save(any(Dog.class))).thenReturn(dog);

        // Then
        assertDoesNotThrow(() -> dogsService.createDog(dog));
        assertEquals(dogsService.createDog(dog), dog);
        verify(dogsRepository, times(2)).save(dog);
    }

    @Test
    public void shouldReserveDog() throws UserNotFoundException, DogAlreadyReserved, DogNotFoundException {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        Long dogId = 1L;
        UserResponseDto user = new UserResponseDto(2L, "John", "+34 447 95 37 85");
        Long userId = 2L;

        // When
        when(dogsRepository.findById(anyLong())).thenReturn(Optional.of(dog));
        when(usersService.getUser(anyLong())).thenReturn(user);
        when(dogsRepository.save(any(Dog.class))).thenReturn(dog);

        // Then
        assertDoesNotThrow(() -> dogsService.reserveDog(dogId, userId));
        dog.setReserveId(null);
        assertEquals(dogsService.reserveDog(dogId, userId), dog);
        verify(dogsRepository, times(2)).findById(dogId);
        verify(usersService, times(2)).getUser(userId);
        verify(dogsRepository, times(2)).save(dog);
    }

    @Test
    public void shouldCancelReserve() throws DogNotFoundException {
        // Given
        Dog dog = new Dog(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        Long dogId = 1L;

        // When
        when(dogsRepository.findById(anyLong())).thenReturn(Optional.of(dog));
        when(dogsRepository.save(any(Dog.class))).thenReturn(dog);

        // Then
        assertDoesNotThrow(() -> dogsService.cancelReserve(dogId));
        assertEquals(dogsService.cancelReserve(dogId), dog);
        verify(dogsRepository, times(2)).findById(dogId);
        verify(dogsRepository, times(2)).save(dog);
    }

    @Test
    public void shouldDeleteDog() {
        // Given
        Long dogId = 1L;

        // When
        when(dogsRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(dogsRepository).deleteById(anyLong());

        // Then
        assertDoesNotThrow(() -> dogsService.deleteDog(dogId));
        verify(dogsRepository, times(1)).existsById(dogId);
        verify(dogsRepository, times(1)).deleteById(dogId);
    }

}
