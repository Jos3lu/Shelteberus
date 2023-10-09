package com.hiberus.mappers;

import com.hiberus.dtos.DogRequestDto;
import com.hiberus.dtos.DogResponseDto;
import com.hiberus.models.Dog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DogsMapper {
    DogResponseDto dogToDtoResponse(Dog dog);
    Dog dtoRequestToDog(DogRequestDto dogRequestDto);
}
