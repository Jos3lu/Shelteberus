package com.hiberus.controllers;

import com.hiberus.dtos.DogDto;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.mappers.DogsMapper;
import com.hiberus.services.DogsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/dogs")
public class DogsController {
    @Autowired
    private DogsService dogsService;
    @Autowired
    private DogsMapper dogsMapper;

    @GetMapping
    @Operation(summary = "Get dogs")
    ResponseEntity<List<DogDto>> getDogs() {
        List<DogDto> dogsDto = dogsService.getDogs().stream()
                .map(dogsMapper::dogToDto)
                .toList();
        return ResponseEntity.ok(dogsDto);
    }

    @GetMapping(value = "/{dogId}")
    @Operation(summary = "Get a dog by its ID")
    ResponseEntity<DogDto> getDog(@PathVariable Long dogId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDto(dogsService.getDog(dogId)));
        } catch (DogNotFoundException dogNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

}
