package com.hiberus.services;

import com.hiberus.dtos.UserDto;
import com.hiberus.exceptions.UserNotFoundException;

public interface UsersService {
    UserDto getUser(Long userId) throws UserNotFoundException;
}
