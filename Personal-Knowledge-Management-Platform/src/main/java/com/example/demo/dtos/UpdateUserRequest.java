package com.example.demo.dtos;


import com.example.demo.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    @NotNull
    @NotEmpty
    @Size(min = 6,max=100)
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 6,max=50)
    private String name;

    public User toEntity(UpdateUserRequest registerUserDTO){
        User user= new User();
        user.setEmail(registerUserDTO.email);
        user.setName(registerUserDTO.name);
        return user;
    }
}
