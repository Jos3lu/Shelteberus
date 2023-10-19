package service;


import com.hiberus.client.ClientDogs;
import com.hiberus.dtos.DogResponseDto;
import com.hiberus.enums.Breed;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.services.impl.DogsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DogsServiceTest {

    @Mock
    private ClientDogs clientDogs;

    @InjectMocks
    private DogsServiceImpl dogsService;

    @Test
    public void shouldGetDog() throws DogNotFoundException {
        // Given
        DogResponseDto dog = new DogResponseDto(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        Long dogId = 1L;

        // When
        when(clientDogs.getDog(anyLong())).thenReturn(ResponseEntity.ok(dog));

        // Then
        assertDoesNotThrow(() -> dogsService.getDog(dogId));
        assertEquals(dogsService.getDog(dogId), dog);
        verify(clientDogs, times(2)).getDog(dogId);
    }

    @Test
    public void shouldDeleteDog() {
        // Given
        Long dogId = 1L;

        // When
        when(clientDogs.deleteDog(anyLong())).thenReturn(ResponseEntity.ok(null));

        // Then
        assertDoesNotThrow(() -> dogsService.deleteDog(dogId));
        verify(clientDogs, times(1)).deleteDog(dogId);
    }

}
