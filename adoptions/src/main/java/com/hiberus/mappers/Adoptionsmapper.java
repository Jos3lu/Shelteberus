package com.hiberus.mappers;

import com.hiberus.dtos.AdoptionRequestDto;
import com.hiberus.dtos.AdoptionResponseDto;
import com.hiberus.models.Adoption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Adoptionsmapper {
    Adoption dtoRequestToAdoption(AdoptionRequestDto adoptionRequestDto);
    AdoptionResponseDto adoptionToResponseDto(Adoption adoption);
}
