package com.hiberus.services;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.exceptions.UserNotValidException;
import com.hiberus.exceptions.UserWithReservationsException;
import com.hiberus.models.User;

import java.util.List;

public interface UsersService {
    /**
     * Get users
     *
     * @return List of users
     */
    List<User> getUsers();

    /**
     * Get user by ID
     *
     * @param userId
     * @return
     */
    User getUser(Long userId) throws UserNotFoundException;

    /**
     * Create user
     *
     * @param user User
     * @return User
     */
    User createUser(User user) throws UserNotValidException, UserAlreadyExistsException;

    /**
     * Update an existing user
     *
     * @param userId User ID
     * @param user User
     * @return User
     */
    User updateUser(Long userId, User user) throws UserNotFoundException;

    /**
     * Delete an existing user
     *
     * @param userId User ID
     */
    void deleteUser(Long userId) throws UserWithReservationsException;
}
