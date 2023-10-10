package com.hiberus.services;

import com.hiberus.exceptions.*;
import com.hiberus.models.Volunteer;

import java.util.List;

public interface VolunteersService {
    /**
     * Get volunteers
     *
     * @return List of Volunteers
     */
    List<Volunteer> getVolunteers();

    Volunteer getVolunteer(Long volunteerId) throws VolunteerNotFoundException;

    /**
     * Create a new volunteer
     *
     * @param volunteer Volunteer
     * @return Volunteer
     */
    Volunteer createVolunteer(Volunteer volunteer) throws VolunteerNotValidException, VolunteerAlreadyExistsException;

    /**
     * Update volunteer by ID
     *
     * @param volunteerId Volunteer ID
     * @param volunteer Volunteer
     * @return Volunteer
     */
    Volunteer updateVolunteer(Long volunteerId, Volunteer volunteer) throws VolunteerNotFoundException;

    /**
     * Delete an existing volunteer
     *
     * @param volunteerId Volunteer ID
     */
    void deleteVolunteer(Long volunteerId);

    /**
     * Add dog to volunteer
     *
     * @param volunteerId Volunteer ID
     * @param dogId Dog ID
     * @return Volunteer
     */
    Volunteer addDogToVolunteer(Long volunteerId, Long dogId) throws VolunteerNotFoundException, DogNotFoundException, DogAlreadyAssociatedToVolunteerException;

    /**
     * Remove dog from volunteer
     *
     * @param volunteerId Volunteer ID
     * @param dogId Dog ID
     * @return Volunteer
     */
    Volunteer deleteDogFromVolunteer(Long volunteerId, Long dogId) throws VolunteerNotFoundException;
}
