package com.hiberus.services.impl;

import com.hiberus.dtos.DogResponseDto;
import com.hiberus.exceptions.*;
import com.hiberus.models.Volunteer;
import com.hiberus.repositories.VolunteersRepository;
import com.hiberus.services.DogsService;
import com.hiberus.services.VolunteersService;
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
    public Volunteer createVolunteer(Volunteer volunteer) throws VolunteerNotValidException, VolunteerAlreadyExistsException {
        volunteer.validVolunteer();
        if (volunteersRepository.existsByName(volunteer.getName())) {
            throw new VolunteerAlreadyExistsException(volunteer.getName());
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
    public Volunteer addDogToVolunteer(Long volunteerId, Long dogId) throws VolunteerNotFoundException,
            DogNotFoundException, DogAlreadyAssociatedToVolunteerException {
        // Get volunteer & dog
        Volunteer volunteer = getVolunteer(volunteerId);
        DogResponseDto dogResponseDto = dogsService.getDog(dogId);
        // Add dog to volunteer
        if (volunteer.getDogs().contains(dogResponseDto.getId()))
            throw new DogAlreadyAssociatedToVolunteerException();
        volunteer.getDogs().add(dogResponseDto.getId());

        return volunteersRepository.save(volunteer);
    }

    @Override
    public Volunteer deleteDogFromVolunteer(Long volunteerId, Long dogId) throws VolunteerNotFoundException {
        Volunteer volunteer = getVolunteer(volunteerId);
        volunteer.getDogs().remove(dogId);
        return volunteersRepository.save(volunteer);
    }
}
