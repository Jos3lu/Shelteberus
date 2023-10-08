package com.hiberus.mappers;

import com.hiberus.dtos.DogDto;
import com.hiberus.models.Dog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DogsMapper {
    DogDto dogToDto(Dog dog);
    Dog dtoToDog(DogDto dogDto);
}
