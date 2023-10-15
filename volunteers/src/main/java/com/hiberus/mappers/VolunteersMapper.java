package com.hiberus.mappers;

import com.hiberus.dtos.VolunteerDogsResponseDto;
import com.hiberus.dtos.VolunteerRequestDto;
import com.hiberus.dtos.VolunteerResponseDto;
import com.hiberus.models.Volunteer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VolunteersMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "dogs")
    Volunteer dtoRequestToVolunteer(VolunteerRequestDto volunteerRequestDto);
    VolunteerResponseDto volunteerToDtoResponse(Volunteer volunteer);
    @Mapping(target = "dogs", ignore = true)
    VolunteerDogsResponseDto volunteerToVolunteerDogsDtoResponse(Volunteer volunteer);
}
