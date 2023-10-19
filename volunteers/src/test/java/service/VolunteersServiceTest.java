package service;

import com.hiberus.dtos.DogResponseDto;
import com.hiberus.enums.Breed;
import com.hiberus.exceptions.*;
import com.hiberus.models.Volunteer;
import com.hiberus.repositories.VolunteersRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.impl.VolunteersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class VolunteersServiceTest {

    @Mock
    private VolunteersRepository volunteersRepository;

    @Mock
    private DogsService dogsService;

    @InjectMocks
    private VolunteersServiceImpl volunteersService;

    @Test
    public void shouldGetVolunteers() {
        // When
        when(volunteersRepository.findAll()).thenReturn(new ArrayList<>());

        // Then
        assertDoesNotThrow(volunteersService::getVolunteers);
        assertEquals(volunteersService.getVolunteers(), new ArrayList<>());
        verify(volunteersRepository, times(2)).findAll();
    }

    @Test
    public void shouldGetVolunteer() throws DogNotFoundException, VolunteerNotFoundException {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", List.of(2L, 3L, 4L, 5L));
        Long volunteerId = 1L;

        // When
        when(volunteersRepository.findById(anyLong())).thenReturn(Optional.of(volunteer));

        // Then
        assertDoesNotThrow(() -> volunteersService.getVolunteer(volunteerId));
        assertEquals(volunteersService.getVolunteer(volunteerId), volunteer);
        verify(volunteersRepository, times(2)).findById(volunteerId);
    }

    @Test
    public void shouldCreateVolunteer() throws VolunteerNotValidException, VolunteerAlreadyExistsException {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", List.of(2L, 3L, 4L, 5L));

        // When
        when(volunteersRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Then
        assertDoesNotThrow(() -> volunteersService.createVolunteer(volunteer));
        assertEquals(volunteersService.createVolunteer(volunteer), volunteer);
        verify(volunteersRepository, times(2)).save(volunteer);
    }

    @Test
    public void shouldUpdateVolunteer() throws VolunteerNotFoundException {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", List.of(2L, 3L, 4L, 5L));
        Long volunteerId = 1L;

        // When
        when(volunteersRepository.existsById(anyLong())).thenReturn(true);
        when(volunteersRepository.findById(anyLong())).thenReturn(Optional.of(volunteer));
        when(volunteersRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Then
        assertDoesNotThrow(() -> volunteersService.updateVolunteer(volunteerId, volunteer));
        assertEquals(volunteersService.updateVolunteer(volunteerId, volunteer), volunteer);
        verify(volunteersRepository, times(2)).existsById(volunteerId);
        verify(volunteersRepository, times(2)).existsById(volunteerId);
        verify(volunteersRepository, times(2)).save(volunteer);
    }

    @Test
    public void shouldDeleteVolunteer() {
        // Given
        Long volunteerId = 1L;

        // When
        when(volunteersRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(volunteersRepository).deleteById(anyLong());

        // Then
        assertDoesNotThrow(() -> volunteersService.deleteVolunteer(volunteerId));
        verify(volunteersRepository, times(1)).existsById(volunteerId);
        verify(volunteersRepository, times(1)).deleteById(volunteerId);
    }

    @Test
    public void shouldAddDogToVolunteer() throws DogNotFoundException, VolunteerNotFoundException, DogAlreadyAssociatedToVolunteerException {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", new ArrayList<>(Arrays.asList(2L, 3L, 4L, 5L)));
        Long volunteerId = 1L;
        DogResponseDto dog = new DogResponseDto(7L, "Firulais", Breed.BEAGLE, LocalDate.now(), null);
        Long dogId = 7L;

        // When
        when(volunteersRepository.findById(anyLong())).thenReturn(Optional.of(volunteer));
        when(dogsService.getDog(anyLong())).thenReturn(dog);
        when(volunteersRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Then
        assertDoesNotThrow(() -> volunteersService.addDogToVolunteer(volunteerId, dogId));
        volunteer.getDogs().remove(7L);
        assertEquals(volunteersService.addDogToVolunteer(volunteerId, dogId), volunteer);
        verify(volunteersRepository, times(2)).findById(volunteerId);
        verify(dogsService, times(2)).getDog(dogId);
        verify(volunteersRepository, times(2)).save(volunteer);
    }

    @Test
    public void shouldDeleteDogFromVolunteer() throws VolunteerNotFoundException {
        // Given
        Volunteer volunteer = new Volunteer(1L, "Sam", "+36 474 95 39 27", new ArrayList<>(Arrays.asList(2L, 3L, 4L, 5L)));
        Long volunteerId = 1L;
        Long dogId = 5L;

        // When
        when(volunteersRepository.findById(anyLong())).thenReturn(Optional.of(volunteer));
        when(volunteersRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        // Then
        assertDoesNotThrow(() -> volunteersService.deleteDogFromVolunteer(volunteerId, dogId));
        volunteer.getDogs().add(5L);
        assertEquals(volunteersService.deleteDogFromVolunteer(volunteerId, dogId), volunteer);
        verify(volunteersRepository, times(2)).findById(volunteerId);
        verify(volunteersRepository, times(2)).save(volunteer);
    }

}
