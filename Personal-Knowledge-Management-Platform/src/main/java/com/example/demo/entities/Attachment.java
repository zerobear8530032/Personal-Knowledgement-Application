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
    Long id;
    @NotBlank
    String fileName;
//    @NotBlank // nullable for now
    String url;

    @NotBlank
    String originalName;

    @NotBlank
    String fileType;

    long size;

    @ManyToOne
    @JoinColumn(name = "note_id",nullable = false)
    Note note;



    public AttachmentResponse toDTO(){
        return new AttachmentResponse(id,originalName,fileType,size,note.getId());
    }
}
