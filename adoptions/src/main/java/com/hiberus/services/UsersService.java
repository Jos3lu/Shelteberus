package com.hiberus.services;

import com.hiberus.dtos.UserResponseDto;
import com.hiberus.exceptions.UserNotFoundException;

public interface UsersService {
    /**
     * Get user by ID
     *
     * @param userId User ID
     * @return User
     */
    UserResponseDto getUser(Long userId) throws UserNotFoundException;
}
