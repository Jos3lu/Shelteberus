package com.hiberus.services.impl;

import com.hiberus.dtos.UserResponseDto;
import com.hiberus.exceptions.DogAlreadyReserved;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.exceptions.DogNotValidException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.Dog;
import com.hiberus.repositories.DogsRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DogsServiceImpl implements DogsService {
    @Autowired
    private DogsRepository dogsRepository;

    @Autowired
    private UsersService usersService;

    @Override
    public List<Dog> getDogs() {
        log.info("Dogs sent");
        return dogsRepository.findAll();
    }

    @Override
    public List<Dog> getUserReservedDogs(Long userId) {
        log.info("Reserved dogs of {} sent", userId);
        return dogsRepository.findByReserveId(userId);
    }

    @Override
    public List<Dog> getVolunteerDogs(List<Long> dogsId) {
        log.info("Dogs associated to a volunteer sent");
        return dogsRepository.findAllById(dogsId);
    }

    @Override
    public Dog getDog(Long dogId) throws DogNotFoundException {
        return dogsRepository.findById(dogId)
                .orElseThrow(() -> new DogNotFoundException(dogId));
    }

    @Override
    public Dog createDog(Dog dog) throws DogNotValidException {
        dog.validDog();
        log.info("Dog {} created", dog.getName());
        return dogsRepository.save(dog);
    }

    @Override
    public Dog reserveDog(Long dogId, Long userId) throws DogNotFoundException,
            DogAlreadyReserved, UserNotFoundException {
        Dog dog = getDog(dogId);
        if (dog.dogAlreadyReserved()) throw new DogAlreadyReserved();

        UserResponseDto userResponseDto = usersService.getUser(userId);
        dog.setReserveId(userResponseDto.getId());
        log.info("Dog {} reserved", dog.getId());
        return dogsRepository.save(dog);
    }

    @Override
    public Dog cancelReserve(Long dogId) throws DogNotFoundException {
        Dog dog = getDog(dogId);
        dog.setReserveId(null);
        log.info("Reserve of {} cancelled", dog.getId());
        return dogsRepository.save(dog);
    }

    @Override
    public void deleteDog(Long dogId) {
        if (dogsRepository.existsById(dogId)) {
            log.info("Dog {} deleted", dogId);
            dogsRepository.deleteById(dogId);
        }
    }
}
