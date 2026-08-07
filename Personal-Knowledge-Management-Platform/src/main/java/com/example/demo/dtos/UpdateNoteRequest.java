package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateNoteRequest {

    @NotBlank
    private String title;

    @NotNull
    private String content;
}
