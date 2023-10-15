package com.hiberus.mappers;

import com.hiberus.dtos.AdoptionRequestDto;
import com.hiberus.dtos.AdoptionResponseDto;
import com.hiberus.models.Adoption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdoptionsMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "adoptionDay")
    Adoption dtoRequestToAdoption(AdoptionRequestDto adoptionRequestDto);
    AdoptionResponseDto adoptionToDtoResponse(Adoption adoption);
}
