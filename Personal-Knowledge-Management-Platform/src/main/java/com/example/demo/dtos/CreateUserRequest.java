package com.example.demo.dtos;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    @Email
    @NotNull
    @NotEmpty
    @Size(min = 6,max=100)
    private String email;
    private String password;
    private Role role;
}
