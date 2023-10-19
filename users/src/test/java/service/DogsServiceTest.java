package service;

import com.hiberus.client.ClientDogs;
import com.hiberus.dtos.DogResponseDto;
import com.hiberus.enums.Breed;
import com.hiberus.services.impl.DogsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
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
    public void shouldGetUserReservedDogs() {
        // Given
        DogResponseDto dog = new DogResponseDto(1L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);
        Long userId = 1L;

        // When
        when(clientDogs.getUserReservedDogs(anyLong())).thenReturn(ResponseEntity.ok(List.of(dog)));

        // Then
        assertDoesNotThrow(() -> dogsService.getUserReservedDogs(userId));
        assertEquals(dogsService.getUserReservedDogs(userId), List.of(dog));
        verify(clientDogs, times(2)).getUserReservedDogs(userId);
    }

}
