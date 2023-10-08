package com.hiberus.services.impl;

import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.models.Dog;
import com.hiberus.repositories.DogsRepository;
import com.hiberus.services.DogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DogsServiceImpl implements DogsService {
    @Autowired
    private DogsRepository dogsRepository;

    @Override
    public List<Dog> getDogs() {
        return dogsRepository.findAll();
    }

    @Override
    public Dog getDog(Long dogId) throws DogNotFoundException {
        return dogsRepository.findById(dogId)
                .orElseThrow(() -> new DogNotFoundException(dogId));
    }
}
