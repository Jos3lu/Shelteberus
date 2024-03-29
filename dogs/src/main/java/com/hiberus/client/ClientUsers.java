package com.hiberus.client;

import com.hiberus.dtos.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users")
public interface ClientUsers {
    @GetMapping(value = "/api/users/{userId}")
    ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId);
}
