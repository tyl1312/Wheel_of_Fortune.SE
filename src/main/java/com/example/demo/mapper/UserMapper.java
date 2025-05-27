package com.example.demo.mapper;

import com.example.demo.request.UpdateUserRequest;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
