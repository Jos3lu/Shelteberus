package com.hiberus.mappers;

import com.hiberus.dtos.UserRequestDto;
import com.hiberus.dtos.UserResponseDto;
import com.hiberus.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    User dtoRequestToUser(UserRequestDto userRequestDto);
    UserResponseDto userToDtoResponse(User user);
}
