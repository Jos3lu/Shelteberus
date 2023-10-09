package com.hiberus.services;

import com.hiberus.dtos.DogResponseDto;

import java.util.List;

public interface DogsService {
    /**
     * Get dog by ID
     *
     * @param dogId Dog ID
     * @return Dog
     */
    DogResponseDto getDog(Long dogId);

    /**
     * Get dogs associated to a volunteer
     *
     * @param dogsId Dogs ID
     * @return Dog
     */
    List<DogResponseDto> getVolunteerDogs(List<Long> dogsId);
}
