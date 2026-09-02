package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttachmentResponse {
    Long id;
    String fileName;
    String fileType;
    long size;
    Long noteId;
}
