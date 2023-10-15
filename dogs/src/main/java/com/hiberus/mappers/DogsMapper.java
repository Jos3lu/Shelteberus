package com.hiberus.mappers;

import com.hiberus.dtos.DogRequestDto;
import com.hiberus.dtos.DogResponseDto;
import com.hiberus.models.Dog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DogsMapper {
    DogResponseDto dogToDtoResponse(Dog dog);
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "reserveId")
    Dog dtoRequestToDog(DogRequestDto dogRequestDto);
}
