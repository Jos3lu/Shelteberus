package com.hiberus.mappers;

import com.hiberus.dtos.UserRequestDto;
import com.hiberus.dtos.UserResponseDto;
import com.hiberus.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    @Mapping(ignore = true, target = "id")
    User dtoRequestToUser(UserRequestDto userRequestDto);
    UserResponseDto userToDtoResponse(User user);
}
