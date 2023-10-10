package com.hiberus.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequestDto {
    private Long userId;
    private Long dogId;
}
