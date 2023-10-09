package com.hiberus.services;

import com.hiberus.exceptions.AdoptionNotFoundException;
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
     * Get adoption by ID
     *
     * @param adoptionId
     * @return
     */
    Adoption getAdoption(Long adoptionId) throws AdoptionNotFoundException;
}
