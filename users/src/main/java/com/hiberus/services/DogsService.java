package com.hiberus.services;

import com.hiberus.dtos.DogResponseDto;

import java.util.List;

public interface DogsService {
    /**
     * Get dogs reserved by a user
     *
     * @param userId User ID
     * @return List of dogs
     */
    List<DogResponseDto> getUserReservedDogs(Long userId);
}
