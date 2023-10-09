package com.hiberus.services.impl;

import com.hiberus.dtos.DogResponseDto;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.exceptions.UserNotValidException;
import com.hiberus.exceptions.UserWithReservationsException;
import com.hiberus.models.User;
import com.hiberus.repositories.UsersRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DogsService dogsService;

    @Override
    public List<User> getUsers() {
        log.info("Users sent");
        return usersRepository.findAll();
    }

    @Override
    public User getUser(Long userId) throws UserNotFoundException {
        return usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User createUser(User user) throws UserNotValidException, UserAlreadyExistsException {
        user.validUser();
        if (usersRepository.existsByName(user.getName())) {
            throw new UserAlreadyExistsException(user.getName());
        }
        log.info("User {} created", user.getName());
        return usersRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) throws UserNotFoundException {
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        User oldUser = getUser(userId);
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());

        log.info("Updated user {}", userId);
        return usersRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long userId) throws UserWithReservationsException {
        if (usersRepository.existsById(userId)) {
            List<DogResponseDto> dogResponseDtos = dogsService.getUserReservedDogs(userId);
            if (!dogResponseDtos.isEmpty()) throw new UserWithReservationsException();
            usersRepository.deleteById(userId);
        }
    }
}
