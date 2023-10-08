package com.hiberus.services.impl;

import com.hiberus.client.ClientUsers;
import com.hiberus.dtos.UserDto;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.services.UsersService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    @Autowired
    private ClientUsers clientUsers;

    @CircuitBreaker(name = "users", fallbackMethod = "fallBackGetUser")
    public UserDto getUser(Long userId) throws UserNotFoundException {
        try {
            return clientUsers.getUser(userId).getBody();
        } catch (FeignException e) {
            throw new UserNotFoundException(userId);
        }
    }

    public UserDto fallBackGetUser(Long userId, Throwable throwable) throws UserNotFoundException {
        throw new UserNotFoundException(userId);
    }
}
