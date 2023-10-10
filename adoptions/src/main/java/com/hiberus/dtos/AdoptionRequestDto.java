package com.hiberus.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequestDto {
    private Long userId;
    private Long dogId;
}
