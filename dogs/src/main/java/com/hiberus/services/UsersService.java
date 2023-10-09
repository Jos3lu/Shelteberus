package com.hiberus.services;

import com.hiberus.dtos.UserResponseDto;
import com.hiberus.exceptions.UserNotFoundException;

public interface UsersService {
    /**
     * Get user by ID
     *
     * @param userId User ID
     * @return User
     * @throws UserNotFoundException User not found
     */
    UserResponseDto getUser(Long userId) throws UserNotFoundException;
}
