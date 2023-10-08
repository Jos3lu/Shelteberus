package com.hiberus.controllers;

import com.hiberus.dtos.DogDto;
import com.hiberus.exceptions.DogAlreadyReserved;
import com.hiberus.exceptions.DogNotFoundException;
import com.hiberus.exceptions.DogNotValidException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.mappers.DogsMapper;
import com.hiberus.models.Dog;
import com.hiberus.services.DogsService;
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
@RequestMapping(value = "/api/dogs")
public class DogsController {
    @Autowired
    private DogsService dogsService;
    @Autowired
    private DogsMapper dogsMapper;

    @GetMapping
    @Operation(summary = "Get dogs")
    ResponseEntity<List<DogDto>> getDogs() {
        return ResponseEntity.ok(dogToDto(dogsService.getDogs()));
    }

    @GetMapping(value = "/{userId}")
    ResponseEntity<List<DogDto>> getUserReservedDogs(@PathVariable Long userId) {
        return ResponseEntity.ok(dogToDto(dogsService.getUserReservedDogs(userId)));
    }

    private List<DogDto> dogToDto(List<Dog> dogs) {
        return dogs.stream()
                .map(dogsMapper::dogToDto)
                .toList();
    }

    @GetMapping(value = "/{dogId}")
    @Operation(summary = "Get a dog by its ID")
    ResponseEntity<DogDto> getDog(@PathVariable Long dogId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDto(dogsService.getDog(dogId)));
        } catch (DogNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Add a new dog to the shelter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already exists", content = @Content)
    })
    ResponseEntity<DogDto> createDog(@RequestBody DogDto dogDto) {
        try {
            return new ResponseEntity<>(dogsMapper.dogToDto(dogsService
                    .createDog(dogsMapper.dtoToDog(dogDto))), HttpStatus.CREATED);
        } catch (DogNotValidException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/reserve-dog")
    @Operation(summary = "Make a reservation for a dog")
    @ApiResponse(responseCode = "200", description = "Successfully reserved")
    ResponseEntity<DogDto> reserveDog(@RequestParam Long dogId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDto(dogsService.reserveDog(dogId, userId)));
        } catch (DogNotFoundException | UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DogAlreadyReserved e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/cancel-reserve")
    @Operation(summary = "Cancel a previous reserve")
    @ApiResponse(responseCode = "200", description = "Reservation successfully cancelled")
    ResponseEntity<DogDto> cancelReserve(@RequestParam Long dogId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDto(dogsService.cancelReserve(dogId)));
        } catch (DogNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
