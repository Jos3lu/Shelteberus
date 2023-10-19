package service;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.exceptions.UserNotValidException;
import com.hiberus.models.User;
import com.hiberus.repositories.UsersRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private DogsService dogsService;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    public void shouldGetUsers() {
        // When
        when(usersRepository.findAll()).thenReturn(new ArrayList<>());

        // Then
        assertDoesNotThrow(usersService::getUsers);
        assertEquals(usersService.getUsers(), new ArrayList<>());
        verify(usersRepository, times(2)).findAll();
    }

    @Test
    public void shouldGetUser() throws UserNotFoundException {
        // Given
        User user = new User(1L, "John", "+34 344 85 23 83");
        Long userId = 1L;

        // When
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Then
        assertDoesNotThrow(() -> usersService.getUser(userId));
        assertEquals(usersService.getUser(userId), user);
        verify(usersRepository, times(2)).findById(userId);
    }

    @Test
    public void shouldCreateUser() throws UserNotValidException, UserAlreadyExistsException {
        // Given
        User user = new User(1L, "John", "+34 344 85 23 83");

        // When
        when(usersRepository.save(any(User.class))).thenReturn(user);

        // Then
        assertDoesNotThrow(() -> usersService.createUser(user));
        assertEquals(usersService.createUser(user), user);
        verify(usersRepository, times(2)).save(user);
    }

    @Test
    public void shouldUpdateUser() throws UserNotFoundException {
        // Given
        User user = new User(1L, "John", "+34 344 85 23 83");
        Long userId = 1L;

        // When
        when(usersRepository.existsById(anyLong())).thenReturn(true);
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(usersRepository.save(any(User.class))).thenReturn(user);

        // Then
        assertDoesNotThrow(() -> usersService.updateUser(userId, user));
        assertEquals(usersService.updateUser(userId, user), user);
        verify(usersRepository, times(2)).existsById(userId);
        verify(usersRepository, times(2)).existsById(userId);
        verify(usersRepository, times(2)).save(user);
    }

    @Test
    public void shouldDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        when(usersRepository.existsById(anyLong())).thenReturn(true);
        when(dogsService.getUserReservedDogs(anyLong())).thenReturn(Collections.emptyList());
        doNothing().when(usersRepository).deleteById(anyLong());

        // Then
        assertDoesNotThrow(() -> usersService.deleteUser(userId));
        verify(usersRepository, times(1)).existsById(userId);
        verify(dogsService, times(1)).getUserReservedDogs(userId);
        verify(usersRepository, times(1)).deleteById(userId);
    }

}
