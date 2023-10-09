package com.hiberus.controllers;

import com.hiberus.dtos.DogRequestDto;
import com.hiberus.dtos.DogResponseDto;
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
    ResponseEntity<List<DogResponseDto>> getDogs() {
        return ResponseEntity.ok(dogToDtoResponse(dogsService.getDogs()));
    }

    @GetMapping(value = "/reserved-dogs")
    @Operation(summary = "Get dogs reserved by a user")
    ResponseEntity<List<DogResponseDto>> getUserReservedDogs(@RequestParam Long userId) {
        return ResponseEntity.ok(dogToDtoResponse(dogsService.getUserReservedDogs(userId)));
    }

    @GetMapping(value = "/volunteer-dogs")
    @Operation(summary = "Get dogs taken care of by a volunteer")
    ResponseEntity<List<DogResponseDto>> getVolunteerDogs(@RequestParam List<Long> dogsId) {
        return ResponseEntity.ok(dogToDtoResponse(dogsService.getVolunteerDogs(dogsId)));
    }

    private List<DogResponseDto> dogToDtoResponse(List<Dog> dogs) {
        return dogs.stream()
                .map(dogsMapper::dogToDtoResponse)
                .toList();
    }

    @GetMapping(value = "/{dogId}")
    @Operation(summary = "Get a dog by its ID")
    ResponseEntity<DogResponseDto> getDog(@PathVariable Long dogId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDtoResponse(dogsService.getDog(dogId)));
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
    ResponseEntity<DogResponseDto> createDog(@RequestBody DogRequestDto dogRequestDto) {
        try {
            return new ResponseEntity<>(dogsMapper.dogToDtoResponse(dogsService
                    .createDog(dogsMapper.dtoRequestToDog(dogRequestDto))), HttpStatus.CREATED);
        } catch (DogNotValidException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{dogId}")
    @Operation(summary = "Delete an existing dog")
    @ApiResponse(responseCode = "204", description = "Successfully deleted")
    ResponseEntity<Void> deleteDog(@PathVariable Long dogId) {
        dogsService.deleteDog(dogId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/reserve-dog")
    @Operation(summary = "Make a reservation for a dog")
    @ApiResponse(responseCode = "200", description = "Successfully reserved")
    ResponseEntity<DogResponseDto> reserveDog(@RequestParam Long dogId, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDtoResponse(dogsService.reserveDog(dogId, userId)));
        } catch (DogNotFoundException | UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DogAlreadyReserved e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/cancel-reserve")
    @Operation(summary = "Cancel a previous reserve")
    @ApiResponse(responseCode = "200", description = "Reservation successfully cancelled")
    ResponseEntity<DogResponseDto> cancelReserve(@RequestParam Long dogId) {
        try {
            return ResponseEntity.ok(dogsMapper.dogToDtoResponse(dogsService.cancelReserve(dogId)));
        } catch (DogNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
