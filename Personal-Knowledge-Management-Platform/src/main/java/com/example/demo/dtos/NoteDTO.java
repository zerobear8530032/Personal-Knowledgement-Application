package com.example.demo.dtos;

import com.example.demo.entities.Note;
import jakarta.persistence.Entity;
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
public class NoteDTO {
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

        public static NoteDTO toDTO(Note note){
            NoteDTO noteDTO= new NoteDTO();
            noteDTO.setId(note.getId());
            noteDTO.setTitle(note.getTitle());
            noteDTO.setContent(note.getContent());
            noteDTO.setCreatedAt(note.getCreatedAt());
            noteDTO.setUpdatedAt(note.getUpdatedAt());
            noteDTO.setUserId(note.getUser().getId());
            return noteDTO;
        }
        public static Note toEntity(NoteDTO noteDTO){
            Note note= new Note();
            note.setId(noteDTO.getId());
            note.setTitle(noteDTO.getTitle());
            note.setContent(noteDTO.getContent());
            note.setCreatedAt(noteDTO.getCreatedAt());
            note.setUpdatedAt(noteDTO.getUpdatedAt());
            return note;
        }
}
