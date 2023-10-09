package com.hiberus.services.impl;

import com.hiberus.client.ClientUsers;
import com.hiberus.dtos.UserResponseDto;
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

    @Override
    @CircuitBreaker(name = "get-user", fallbackMethod = "fallBackGetUser")
    public UserResponseDto getUser(Long userId) throws UserNotFoundException {
        try {
            return clientUsers.getUser(userId).getBody();
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException(userId);
        }
    }

    public UserResponseDto fallBackGetUser(Long userId, Throwable throwable) throws UserNotFoundException {
        log.warn("Sent user not found");
        throw new UserNotFoundException(userId);
    }
}
