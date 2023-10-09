package com.hiberus.services.impl;

import com.hiberus.client.ClientDogs;
import com.hiberus.dtos.DogResponseDto;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.services.DogsService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DogsServiceImpl implements DogsService {
    @Autowired
    private ClientDogs clientDogs;

    @Override
    @CircuitBreaker(name = "get-dog", fallbackMethod = "fallBackGetDog")
    public DogResponseDto getDog(Long dogId) throws DogNotFoundException {
        try {
            return clientDogs.getDog(dogId).getBody();
        } catch (FeignException.NotFound e) {
            throw new DogNotFoundException(dogId);
        }
    }

    public DogResponseDto fallBackGetDog(Long dogId, Throwable throwable) throws DogNotFoundException {
        log.warn("Sent dog not found");
        throw new DogNotFoundException(dogId);
    }

    @Override
    @CircuitBreaker(name = "get-volunteer-dogs", fallbackMethod = "fallBackGetVolunteerDogs")
    public List<DogResponseDto> getVolunteerDogs(List<Long> dogsId) {
        return clientDogs.getVolunteerDogs(dogsId).getBody();
    }

    public List<DogResponseDto> fallBackGetVolunteerDogs(List<Long> dogsId, Throwable throwable) {
        log.warn("Sent default list of dogs");
        return Collections.emptyList();
    }
}
