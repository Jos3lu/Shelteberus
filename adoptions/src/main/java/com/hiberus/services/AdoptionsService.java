package com.hiberus.services;

import com.hiberus.models.Adoption;

import java.util.List;

public interface AdoptionsService {
    /**
     * Get adoptions
     *
     * @return List of adoptions
     */
    List<Adoption> getAdoptions();
}
