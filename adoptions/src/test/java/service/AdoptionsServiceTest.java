package service;

import com.hiberus.dtos.DogResponseDto;
import com.hiberus.dtos.UserResponseDto;
import com.hiberus.enums.Breed;
import com.hiberus.exceptions.*;
import com.hiberus.models.Adoption;
import com.hiberus.repositories.AdoptionsRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.UsersService;
import com.hiberus.services.impl.AdoptionsServiceImpl;
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
public class AdoptionsServiceTest {

    @Mock
    private AdoptionsRepository adoptionsRepository;

    @Mock
    private DogsService dogsService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private AdoptionsServiceImpl adoptionsService;

    @Test
    public void shouldGetAdoptions() {
        // When
        when(adoptionsRepository.findAll()).thenReturn(new ArrayList<>());

        // Then
        assertDoesNotThrow(adoptionsService::getAdoptions);
        assertEquals(adoptionsService.getAdoptions(), new ArrayList<>());
        verify(adoptionsRepository, times(2)).findAll();
    }

    @Test
    public void shouldGetAdoptionByUser() throws UserNotFoundException {
        // Given
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());
        Long userId = 2L;

        // When
        when(adoptionsRepository.findByUserId(anyLong())).thenReturn(List.of(adoption));

        // Then
        assertDoesNotThrow(() -> adoptionsService.getAdoptionsByUser(userId));
        assertArrayEquals(adoptionsService.getAdoptionsByUser(userId).toArray(), List.of(adoption).toArray());
        verify(adoptionsRepository, times(2)).findByUserId(userId);
    }

    @Test
    public void shouldGetAdoption() throws AdoptionNotFoundException {
        // Given
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());
        Long adoptionId = 1L;

        // When
        when(adoptionsRepository.findById(anyLong())).thenReturn(Optional.of(adoption));

        // Then
        assertDoesNotThrow(() -> adoptionsService.getAdoption(adoptionId));
        assertEquals(adoptionsService.getAdoption(adoptionId), adoption);
        verify(adoptionsRepository, times(2)).findById(adoptionId);
    }

    @Test
    public void shouldCreateAdoption() throws UserNotFoundException, AdoptionAlreadyExistsException, DogNotFoundException, UserNotReservedDogException {
        // Given
        UserResponseDto user = new UserResponseDto(2L, "John", "+34 447 95 37 85");
        DogResponseDto dog = new DogResponseDto(3L, "Firulais", Breed.BEAGLE, LocalDate.now(), 2L);
        Adoption adoption = new Adoption(1L, 2L, 3L, LocalDate.now());

        // When
        when(dogsService.getDog(anyLong())).thenReturn(dog);
        when(usersService.getUser(anyLong())).thenReturn(user);
        when(adoptionsRepository.existsByUserIdAndDogId(anyLong(), anyLong())).thenReturn(false);
        doNothing().when(dogsService).deleteDog(anyLong());
        when(adoptionsRepository.save(any(Adoption.class))).thenReturn(adoption);

        // Then
        assertDoesNotThrow(() -> adoptionsService.createAdoption(adoption));
        assertEquals(adoptionsService.createAdoption(adoption), adoption);
        verify(adoptionsRepository, times(2)).save(adoption);
    }

}
