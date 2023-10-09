package com.hiberus.dtos;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionResponseDto {
    private Long id;
    private Long userId;
    private Long dogId;
    private Date adoptionDay;
}
