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

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    @Email
    @NotNull
    @NotEmpty
    @Size(min = 6,max=100)
    private String email;


    public static UserResponse toDTO(User user){
        if(user==null){
            return  null;
        }
        List<Long> noteDTOList= user.getUserNotes()!= null ? user.getUserNotes().stream().map((note)->note.getId()).toList() : null;
        UserResponse userDTO= new UserResponse(user.getId(),user.getName(),user.getEmail());
        return userDTO;
    }
    public static User toEntity(UserResponse userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        return user;
    }

}
