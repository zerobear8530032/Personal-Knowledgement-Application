package com.example.demo.entities;

import com.example.demo.dtos.AttachmentResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String fileName;
//    @NotBlank // nullable for now
private String url;

    @NotBlank
    private String originalName;

    @NotBlank
    private String fileType;

    private long size;

    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "note_id",nullable = false)
    private Note note;



}
