package com.hiberus.controllers;

import com.hiberus.dtos.AdoptionResponseDto;
import com.hiberus.exceptions.AdoptionNotFoundException;
import com.hiberus.mappers.Adoptionsmapper;
import com.hiberus.models.Adoption;
import com.hiberus.services.AdoptionsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/adoptions")
public class AdoptionsController {
    @Autowired
    private AdoptionsService adoptionsService;

    @Autowired
    private Adoptionsmapper adoptionsmapper;

    @GetMapping
    @Operation(summary = "Get adoptions")
    ResponseEntity<List<AdoptionResponseDto>> getAdoptions() {
        List<AdoptionResponseDto> adoptionResponseDtos = adoptionsService.getAdoptions().stream()
                .map(adoptionsmapper::adoptionToDtoResponse)
                .toList();
        return ResponseEntity.ok(adoptionResponseDtos);
    }

    @GetMapping(value = "/adoptions-user/{userId}")
    @Operation(summary = "Get adoptions associated to a user")
    ResponseEntity<List<AdoptionResponseDto>> getAdoptionsByUser(@PathVariable Long userId) {
        return
    }

    private List<AdoptionResponseDto> adoptionToDtoResponse(List<Adoption> adoptions) {
        return adoptions.stream()
                .map(adoptionsmapper::adoptionToDtoResponse)
                .toList();
    }

    @GetMapping(value = "/{adoptionId}")
    @Operation(summary = "Get adoption by ID")
    ResponseEntity<AdoptionResponseDto> getAdoption(@PathVariable Long adoptionId) {
        try {
            return ResponseEntity.ok(adoptionsmapper.adoptionToDtoResponse(adoptionsService
                    .getAdoption(adoptionId)));
        } catch (AdoptionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
