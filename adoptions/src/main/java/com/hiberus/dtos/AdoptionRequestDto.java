package com.hiberus.dtos;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequestDto {
    private Long userId;
    private Long dogId;
    private Date adoptionDay;
}
