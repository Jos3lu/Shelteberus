package com.hiberus.controllers;

import com.hiberus.dtos.VolunteerDogsResponseDto;
import com.hiberus.dtos.VolunteerRequestDto;
import com.hiberus.dtos.VolunteerResponseDto;
import com.hiberus.exceptions.*;
import com.hiberus.mappers.VolunteersMapper;
import com.hiberus.models.Volunteer;
import com.hiberus.services.DogsService;
import com.hiberus.services.VolunteersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/volunteers")
public class VolunteersController {
    @Autowired
    private DogsService dogsService;
    @Autowired
    private VolunteersService volunteersService;

    @Autowired
    private VolunteersMapper volunteersMapper;

    @GetMapping
    @Operation(summary = "Get volunteers")
    ResponseEntity<List<VolunteerResponseDto>> getVolunteers() {
        List<VolunteerResponseDto> volunteerResponseDtos = volunteersService.getVolunteers().stream()
                .map(volunteersMapper::volunteerToDtoResponse)
                .toList();
        return ResponseEntity.ok(volunteerResponseDtos);
    }

    @GetMapping(value = "/{volunteerId}")
    @Operation(summary = "Get volunteer by ID")
    ResponseEntity<VolunteerDogsResponseDto> getVolunteer(@PathVariable Long volunteerId) {
        try {
            // Get volunteer
            Volunteer volunteer = volunteersService.getVolunteer(volunteerId);
            VolunteerDogsResponseDto volunteerDogsResponseDto = volunteersMapper
                    .volunteerToVolunteerDogsDtoResponse(volunteer);

            // Get dogs associated to volunteer
            volunteerDogsResponseDto.setDogs(dogsService.getVolunteerDogs(volunteer.getDogs()));

            return ResponseEntity.ok(volunteerDogsResponseDto);
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create new volunteer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already exists", content = @Content)
    })
    ResponseEntity<VolunteerResponseDto> createVolunteer(@RequestBody VolunteerRequestDto volunteerRequestDto) {
        try {
            return new ResponseEntity<>(volunteersMapper.volunteerToDtoResponse(volunteersService
                    .createVolunteer(volunteersMapper.dtoRequestToVolunteer(volunteerRequestDto))),
                    HttpStatus.CREATED);
        } catch (VolunteerNotValidException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (VolunteerAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{volunteerId}")
    @Operation(summary = "Update an existing volunteer")
    ResponseEntity<VolunteerResponseDto> updateVolunteer(@PathVariable Long volunteerId, @RequestBody VolunteerRequestDto volunteerRequestDto) {
        try {
            return ResponseEntity.ok(volunteersMapper.volunteerToDtoResponse(volunteersService
                    .updateVolunteer(volunteerId, volunteersMapper
                            .dtoRequestToVolunteer(volunteerRequestDto))));
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{volunteerId}")
    @Operation(summary = "Delete an existing volunteer")
    @ApiResponse(responseCode = "204", description = "Successfully deleted")
    ResponseEntity<Void> deleteVolunteer(@PathVariable Long volunteerId) {
        volunteersService.deleteVolunteer(volunteerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/add-dog")
    @Operation(summary = "Associate dog to volunteer")
    ResponseEntity<VolunteerResponseDto> addDogToVolunteer(@RequestParam Long volunteerId, @RequestParam Long dogId) {
        try {
            return ResponseEntity.ok(volunteersMapper.volunteerToDtoResponse(volunteersService
                    .addDogToVolunteer(volunteerId, dogId)));
        } catch (VolunteerNotFoundException | DogNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DogAlreadyAssociatedToVolunteerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/delete-dog")
    @Operation(summary = "Remove dgo from volunteer")
    ResponseEntity<VolunteerResponseDto> deleteDogFromVolunteer(@RequestParam Long volunteerId, @RequestParam Long dogId) {
        try {
            return ResponseEntity.ok(volunteersMapper.volunteerToDtoResponse(volunteersService
                    .deleteDogFromVolunteer(volunteerId, dogId)));
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
