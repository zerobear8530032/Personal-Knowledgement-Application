package com.example.demo.dtos;

import com.example.demo.entities.Note;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteRequest {
    @NotBlank
    private String title;

    @NotNull
    private String content;

    Long folderId;


}
