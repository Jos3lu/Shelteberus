package com.hiberus.controllers;

import com.hiberus.dtos.AdoptionResponseDto;
import com.hiberus.mappers.Adoptionsmapper;
import com.hiberus.services.AdoptionsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
                .map(adoptionsmapper::adoptionToResponseDto)
                .toList();
        return ResponseEntity.ok(adoptionResponseDtos);
    }

}
