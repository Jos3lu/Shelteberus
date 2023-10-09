package com.hiberus.services;

import com.hiberus.dtos.DogResponseDto;
import com.hiberus.exceptions.DogNotFoundException;

public interface DogsService {
    /**
     * Get dog by ID
     *
     * @param dogId Dog ID
     * @return Dog
     */
    DogResponseDto getDog(Long dogId) throws DogNotFoundException;

    /**
     * Delete an existing dog
     *
     * @param dogId Dog ID
     */
    void deleteDog(Long dogId) throws DogNotFoundException;
}
