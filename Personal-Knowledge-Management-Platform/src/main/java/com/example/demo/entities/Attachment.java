package com.example.demo.entities;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id
    String id;
    @NotBlank
    String FileName;
    @NotBlank
    String url;

    @NotBlank
    String fileType;

    long bytes;


}
