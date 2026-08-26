package com.example.demo.dtos;

import com.example.demo.entities.Note;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @NotNull
        @NotEmpty
        private String title;
        @NotNull
        @NotEmpty
        private String content;
        @NotNull
        private LocalDateTime createdAt;
        @NotNull
        private LocalDateTime updatedAt;
        private List<AttachmentResponse>  attachmentList;
        private Long  userId;
        private Long folderId;


}
