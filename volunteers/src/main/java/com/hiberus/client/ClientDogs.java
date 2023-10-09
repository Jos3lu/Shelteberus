package com.hiberus.client;

import com.hiberus.dtos.DogResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "dogs")
public interface ClientDogs {
    @GetMapping(value = "/api/dogs/{dogId}")
    ResponseEntity<DogResponseDto> getDog(@PathVariable Long dogId);

    @GetMapping(value = "/api/dogs/volunteer-dogs")
    ResponseEntity<List<DogResponseDto>> getVolunteerDogs(@RequestParam List<Long> dogsId);
}
