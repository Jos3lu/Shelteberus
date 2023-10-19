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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    public void shouldGetVolunteerDogs() {
        // Given
        List<Long> dogsId = List.of(1L, 2L, 3L);
        DogResponseDto dog = new DogResponseDto(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        DogResponseDto dog1 = new DogResponseDto(2L, "Nevado", Breed.SHIBA_INU, LocalDate.now(), null);
        DogResponseDto dog2 = new DogResponseDto(3L, "Max", Breed.HUSKY, LocalDate.now(), null);
        List<DogResponseDto> dogs = List.of(dog, dog1, dog2);

        // When
        when(clientDogs.getVolunteerDogs(anyList())).thenReturn(ResponseEntity.ok(dogs));

        // Then
        assertDoesNotThrow(() -> clientDogs.getVolunteerDogs(dogsId));
        assertArrayEquals(clientDogs.getVolunteerDogs(dogsId).getBody().toArray(), dogs.toArray());
        verify(clientDogs, times(2)).getVolunteerDogs(dogsId);
    }

}
