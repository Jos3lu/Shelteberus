package com.hiberus.dtos;

import com.hiberus.enums.Breed;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogRequestDto {
    private String name;
    private Breed breed;
    private Date birth;
}
