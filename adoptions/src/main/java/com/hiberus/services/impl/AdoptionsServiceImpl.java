package com.hiberus.services.impl;

import com.hiberus.dtos.DogResponseDto;
import com.hiberus.exceptions.*;
import com.hiberus.models.Adoption;
import com.hiberus.repositories.AdoptionsRepository;
import com.hiberus.services.AdoptionsService;
import com.hiberus.services.DogsService;
import com.hiberus.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AdoptionsServiceImpl implements AdoptionsService {
    @Autowired
    private AdoptionsRepository adoptionsRepository;

    @Autowired
    private DogsService dogsService;

    @Autowired
    private UsersService usersService;

    @Override
    public List<Adoption> getAdoptions() {
        log.info("Adoptions sent");
        return adoptionsRepository.findAll();
    }

    @Override
    public List<Adoption> getAdoptionsByUser(Long userId) {
        log.info("Adoption of user {} sent", userId);
        return adoptionsRepository.findByUserId(userId);
    }

    @Override
    public Adoption getAdoption(Long adoptionId) throws AdoptionNotFoundException {
        return adoptionsRepository.findById(adoptionId)
                .orElseThrow(() -> new AdoptionNotFoundException(adoptionId));
    }

    @Override
    public Adoption createAdoption(Adoption adoption) throws DogNotFoundException, UserNotFoundException,
            UserNotReservedDogException, AdoptionAlreadyExistsException {
        DogResponseDto dogResponseDto = dogsService.getDog(adoption.getDogId());
        usersService.getUser(adoption.getUserId());
        if (!adoption.getUserId().equals(dogResponseDto.getReserveId()))
            throw new UserNotReservedDogException(adoption.getUserId(), adoption.getDogId());
        if (adoptionsRepository.existsByUserIdAndDogId(adoption.getUserId(), adoption.getDogId()))
            throw new AdoptionAlreadyExistsException();
        adoption.setAdoptionDay(LocalDate.now());
        dogsService.deleteDog(adoption.getDogId());

        log.info("Adoption created");
        return adoptionsRepository.save(adoption);
    }
}
