package com.example.demo.mappers;

import com.example.demo.dtos.CustomUserDetail;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {
    public CustomUserDetail userEntityToCustomUserDetail(User user);

}
