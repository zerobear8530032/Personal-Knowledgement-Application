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
public class RegisterUserDTO {

    @Email
    @NotNull
    @NotEmpty
    @Size(min = 6,max=100)
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    @Size(min = 6,max=50)
    private String name;

    public static User toEntity(RegisterUserDTO registerUserDTO){
        if(registerUserDTO==null){
            return  null;
        }
        User user= new User();
        user.setEmail(registerUserDTO.email);
        user.setName(registerUserDTO.name);
        user.setPassword(registerUserDTO.password);
        return user;
    }

    public User toEntity(){
        if(this==null){
            return  null;
        }
        User user= new User();
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        return user;
    }
}
