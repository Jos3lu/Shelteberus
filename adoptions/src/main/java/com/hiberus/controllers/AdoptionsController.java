package com.hiberus.controllers;

import com.hiberus.dtos.AdoptionRequestDto;
import com.hiberus.dtos.AdoptionResponseDto;
import com.hiberus.exceptions.*;
import com.hiberus.mappers.AdoptionsMapper;
import com.hiberus.models.Adoption;
import com.hiberus.services.AdoptionsService;
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
@RequestMapping(value = "/api/adoptions")
public class AdoptionsController {
    @Autowired
    private AdoptionsService adoptionsService;

    @Autowired
    private AdoptionsMapper adoptionsMapper;

    @GetMapping
    @Operation(summary = "Get adoptions")
    ResponseEntity<List<AdoptionResponseDto>> getAdoptions() {
        return ResponseEntity.ok(adoptionToDtoResponse(adoptionsService.getAdoptions()));
    }

    @GetMapping(value = "/user/{userId}")
    @Operation(summary = "Get adoptions associated to a user")
    ResponseEntity<List<AdoptionResponseDto>> getAdoptionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adoptionToDtoResponse(adoptionsService.getAdoptionsByUser(userId)));
    }

    private List<AdoptionResponseDto> adoptionToDtoResponse(List<Adoption> adoptions) {
        return adoptions.stream()
                .map(adoptionsMapper::adoptionToDtoResponse)
                .toList();
    }

    @GetMapping(value = "/{adoptionId}")
    @Operation(summary = "Get adoption by ID")
    ResponseEntity<AdoptionResponseDto> getAdoption(@PathVariable Long adoptionId) {
        try {
            return ResponseEntity.ok(adoptionsMapper.adoptionToDtoResponse(adoptionsService
                    .getAdoption(adoptionId)));
        } catch (AdoptionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new adoption")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already exists", content = @Content)
    })
    ResponseEntity<AdoptionResponseDto> createAdoption(@RequestBody AdoptionRequestDto adoptionRequestDto) {
        try {
            return new ResponseEntity<>(adoptionsMapper.adoptionToDtoResponse(adoptionsService
                    .createAdoption(adoptionsMapper.dtoRequestToAdoption(adoptionRequestDto))),
                    HttpStatus.CREATED);
        } catch (UserNotFoundException | DogNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AdoptionAlreadyExistsException | UserNotReservedDogException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
