package com.example.demo.dtos;

import com.example.demo.entities.Folder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FolderResponse {
    Long id;
    String folderName;
    LocalDateTime createAt;
    boolean isDeleted;
    Long userId;
}
