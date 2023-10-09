package com.hiberus.services;

import com.hiberus.exceptions.*;
import com.hiberus.models.Adoption;

import java.util.List;

public interface AdoptionsService {
    /**
     * Get adoptions
     *
     * @return List of adoptions
     */
    List<Adoption> getAdoptions();

    /**
     * Get adoptions associated to a user
     *
     * @param userId User ID
     * @return List of adoptions
     */
    List<Adoption> getAdoptionsByUser(Long userId);

    /**
     * Get adoption by ID
     *
     * @param adoptionId
     * @return
     */
    Adoption getAdoption(Long adoptionId) throws AdoptionNotFoundException;

    /**
     * Create adoption
     *
     * @param adoption Adoption
     * @return Adoption
     */
    Adoption createAdoption(Adoption adoption) throws DogNotFoundException, UserNotFoundException, UserNotReservedDogException, AdoptionAlreadyExistsException;
}
