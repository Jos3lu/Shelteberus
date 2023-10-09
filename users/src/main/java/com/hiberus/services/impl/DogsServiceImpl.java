package com.hiberus.services.impl;

import com.hiberus.client.ClientDogs;
import com.hiberus.dtos.DogResponseDto;
import com.hiberus.exceptions.UserWithReservationsException;
import com.hiberus.services.DogsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DogsServiceImpl implements DogsService {
    @Autowired
    private ClientDogs clientDogs;

    @Override
    @CircuitBreaker(name = "get-reserved-dogs", fallbackMethod = "fallBackGetUserReservedDogs")
    public List<DogResponseDto> getUserReservedDogs(Long userId) {
        return clientDogs.getUserReservedDogs(userId).getBody();
    }

    public List<DogResponseDto> fallBackGetUserReservedDogs(Long userId, Throwable throwable) throws UserWithReservationsException {
        log.warn("Sent user with reservations");
        throw new UserWithReservationsException();
    }
}
