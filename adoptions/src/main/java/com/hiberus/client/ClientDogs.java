package com.hiberus.client;

import com.hiberus.dtos.DogResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dogs")
public interface ClientDogs {
    @GetMapping(value = "/api/dogs/{dogId}")
    ResponseEntity<DogResponseDto> getDog(@PathVariable Long dogId);

    @DeleteMapping(value = "/api/dogs/{dogId}")
    ResponseEntity<Void> deleteDog(@PathVariable Long dogId);
}
