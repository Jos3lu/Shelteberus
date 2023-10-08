package com.hiberus.services;

import com.hiberus.exceptions.DogAlreadyReserved;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.exceptions.DogNotValidException;
import com.hiberus.exceptions.UserNotFoundException;
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
     * Get reserved dogs from a user
     *
     * @return List of dogs
     */
    List<Dog> getUserReservedDogs(Long userId);

    /**
     * Get dog by ID
     *
     * @param dogId Dog ID
     * @return Dog
     * @throws DogNotFoundException Dog not found
     */
    Dog getDog(Long dogId) throws DogNotFoundException;

    /**
     * Add a new dog to the shelter
     *
     * @param dog New dog
     * @returnDog
     * @throws DogNotValidException Dog not valid
     */
    Dog createDog(Dog dog) throws DogNotValidException;

    /**
     * Create reserve for a dog
     *
     * @param dogId Dog ID
     * @param userId User ID
     * @return Dog
     */
    Dog reserveDog(Long dogId, Long userId) throws DogNotFoundException, DogAlreadyReserved, UserNotFoundException;

    /**
     * Cancel a previous reserve
     *
     * @param dogId Dog ID
     * @return Dog
     */
    Dog cancelReserve(Long dogId) throws DogNotFoundException;
}
