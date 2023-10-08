package com.hiberus.services;

import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.models.Dog;

import java.util.List;

public interface DogsService {
    /**
     * Get dogs
     *
     * @return List of dogs
     */
    List<Dog> getDogs();

    /**
     * Get dog by ID
     *
     * @param dogId Dog ID
     * @return Dog
     * @throws DogNotFoundException Dog not found
     */
    Dog getDog(Long dogId) throws DogNotFoundException;
}
