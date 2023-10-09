package com.hiberus.services.impl;

import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.exceptions.VolunteerAlreadyExists;
import com.hiberus.exceptions.VolunteerNotFoundException;
import com.hiberus.exceptions.VolunteerNotValidException;
import com.hiberus.models.Volunteer;
import com.hiberus.repositories.VolunteersRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.VolunteersService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VolunteersServiceImpl implements VolunteersService {
    @Autowired
    private VolunteersRepository volunteersRepository;

    @Autowired
    private DogsService dogsService;

    @Override
    public List<Volunteer> getVolunteers() {
        log.info("Volunteers sent");
        return volunteersRepository.findAll();
    }

    @Override
    public Volunteer getVolunteer(Long volunteerId) throws VolunteerNotFoundException {
        return volunteersRepository.findById(volunteerId)
                .orElseThrow(() -> new VolunteerNotFoundException(volunteerId));
    }

    @Override
    public Volunteer createVolunteer(Volunteer volunteer) throws VolunteerNotValidException, VolunteerAlreadyExists {
        volunteer.validVolunteer();
        if (volunteersRepository.existsByName(volunteer.getName())) {
            throw new VolunteerAlreadyExists(volunteer.getName());
        }
        log.info("Volunteer {} created", volunteer.getName());
        return volunteersRepository.save(volunteer);
    }

    @Override
    public Volunteer updateVolunteer(Long volunteerId, Volunteer volunteer) throws VolunteerNotFoundException {
        if (!volunteersRepository.existsById(volunteerId)) {
            throw new VolunteerNotFoundException(volunteerId);
        }
        Volunteer oldVolunteer = getVolunteer(volunteerId);
        oldVolunteer.setName(volunteer.getName());
        oldVolunteer.setPhone(volunteer.getPhone());

        log.info("Updated volunteer {}", volunteerId);
        return volunteersRepository.save(oldVolunteer);
    }

    @Override
    public void deleteVolunteer(Long volunteerId) {
        if (volunteersRepository.existsById(volunteerId)) {
            log.info("Volunteer {} deleted", volunteerId);
            volunteersRepository.deleteById(volunteerId);
        }
    }

    @Override
    public Volunteer addDogToVolunteer(Long volunteerId, Long dogId) throws VolunteerNotFoundException, DogNotFoundException {
        Volunteer volunteer = getVolunteer(volunteerId);
        try {
            dogsService.getDog(dogId);
        } catch (FeignException.NotFound e) {
            throw new DogNotFoundException(dogId);
        }

        volunteer.getDogs().add(dogId);
        return volunteersRepository.save(volunteer);
    }

    @Override
    public Volunteer deleteDogFromVolunteer(Long volunteerId, Long dogId) throws VolunteerNotFoundException {
        Volunteer volunteer = getVolunteer(volunteerId);
        volunteer.getDogs().remove(dogId);
        return volunteersRepository.save(volunteer);
    }
}
