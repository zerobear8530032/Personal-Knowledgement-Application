package com.example.demo.dtos;

import com.example.demo.entities.Note;
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
public class UserDTO {
    private Long id;
    private String name;
    @Email
    @NotNull
    @NotEmpty
    @Size(min = 6,max=100)
    private String email;

    private List<Long> noteDTO;

    public static UserDTO toDTO(User user){
        if(user==null){
            return  null;
        }
        List<Long> noteDTOList= user.getUserNotes()!= null ? user.getUserNotes().stream().map((note)->note.getId()).toList() : null;
        UserDTO userDTO= new UserDTO(user.getId(),user.getName(),user.getEmail(),noteDTOList);
        return userDTO;
    }
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        return user;
    }

}
