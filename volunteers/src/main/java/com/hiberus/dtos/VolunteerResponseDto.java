package com.hiberus.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerResponseDto {
    private Long id;
    private String name;
    private String phone;
    private List<Long> dogs;
}
