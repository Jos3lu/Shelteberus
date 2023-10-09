package com.hiberus.client;

import com.hiberus.dtos.DogResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "dogs")
public interface ClientDogs {
    @GetMapping(value = "/api/dogs/reserved-dogs")
    ResponseEntity<List<DogResponseDto>> getUserReservedDogs(@RequestParam Long userId);
}
