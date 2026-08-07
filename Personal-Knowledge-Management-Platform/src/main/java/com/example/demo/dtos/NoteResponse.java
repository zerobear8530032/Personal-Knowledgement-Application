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

        Long  userId;

        public static NoteResponse toDTO(Note note){
            NoteResponse noteResponse = new NoteResponse();
            noteResponse.setId(note.getId());
            noteResponse.setTitle(note.getTitle());
            noteResponse.setContent(note.getContent());
            noteResponse.setCreatedAt(note.getCreatedAt());
            noteResponse.setUpdatedAt(note.getUpdatedAt());
            noteResponse.setUserId(note.getUser().getId());
            return noteResponse;
        }
        public static Note toEntity(NoteResponse noteResponse){
            Note note= new Note();
            note.setId(noteResponse.getId());
            note.setTitle(noteResponse.getTitle());
            note.setContent(noteResponse.getContent());
            note.setCreatedAt(noteResponse.getCreatedAt());
            note.setUpdatedAt(noteResponse.getUpdatedAt());
            return note;
        }
}
