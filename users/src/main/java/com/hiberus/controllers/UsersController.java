package com.hiberus.controllers;

import com.hiberus.dtos.UserRequestDto;
import com.hiberus.dtos.UserResponseDto;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.exceptions.UserNotValidException;
import com.hiberus.exceptions.UserWithReservationsException;
import com.hiberus.mappers.UsersMapper;
import com.hiberus.services.UsersService;
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
@RequestMapping(value = "/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersMapper usersMapper;

    @GetMapping
    @Operation(summary = "Get users")
    ResponseEntity<List<UserResponseDto>> getusers() {
        List<UserResponseDto> userResponseDtos = usersService.getUsers().stream()
                .map(usersMapper::userToResponseDto)
                .toList();
        return ResponseEntity.ok(userResponseDtos);
    }

    @GetMapping(value = "/{userId}")
    @Operation(summary = "Get user by ID")
    ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(usersMapper.userToResponseDto(usersService.getUser(userId)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "409", description = "Already exists", content = @Content)
    })
    ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            return new ResponseEntity<>(usersMapper.userToResponseDto(usersService
                    .createUser(usersMapper.dtoRequestToUser(userRequestDto))), HttpStatus.CREATED);
        } catch (UserNotValidException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{userId}")
    @Operation(summary = "Update an existing user")
    ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        try {
            return ResponseEntity.ok(usersMapper.userToResponseDto(usersService
                    .updateUser(userId, usersMapper.dtoRequestToUser(userRequestDto))));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Delete an existing user")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            usersService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (UserWithReservationsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
