package com.hiberus.dtos;

import com.hiberus.enums.Breed;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogResponseDto {
    private Long id;
    private String name;
    private Breed breed;
    private LocalDate birth;
    private Long reserveId;
}
