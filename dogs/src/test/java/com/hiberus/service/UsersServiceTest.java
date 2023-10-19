package com.hiberus.service;

import com.hiberus.client.ClientUsers;
import com.hiberus.dtos.UserResponseDto;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.services.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UsersServiceTest {

    @Mock
    private ClientUsers clientUsers;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    public void shouldGetUser() throws UserNotFoundException {
        // Given
        UserResponseDto user = new UserResponseDto(2L, "John", "+34 447 95 37 85");
        Long userId = 2L;

        // When
        when(clientUsers.getUser(anyLong())).thenReturn(ResponseEntity.ok(user));

        // Then
        assertDoesNotThrow(() -> usersService.getUser(userId));
        assertEquals(usersService.getUser(userId), user);
        verify(clientUsers, times(2)).getUser(userId);
    }

}
