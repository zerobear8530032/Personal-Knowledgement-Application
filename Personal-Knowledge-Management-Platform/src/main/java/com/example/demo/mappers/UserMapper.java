package com.example.demo.mappers;

import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.UserResponse;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public User registerUserToEntity(RegisterUserRequest registerUserRequest);
    public UserResponse userEntityToUserResponse(User user);
}
