package com.example.demo.dtos;

import com.example.demo.entities.Note;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteDTO {
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String content;

    public Note toEntity(CreateNoteDTO createNoteDTO){
        Note note= new Note();
        note.setContent(createNoteDTO.getContent());
        note.setTitle(createNoteDTO.getTitle());
        return note;
    }

}
