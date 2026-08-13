package com.example.demo.entities;
import com.example.demo.dtos.NoteResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    User user;

    public NoteResponse toDTO(){
        NoteResponse noteResponse= new NoteResponse();
        noteResponse.setId(this.id);
        noteResponse.setTitle(this.title);
        noteResponse.setContent(this.content);
        noteResponse.setCreatedAt(this.createdAt);
        noteResponse.setUpdatedAt(this.updatedAt);
        noteResponse.setUserId(this.user.getId());
        return noteResponse;
    }

    @ManyToOne(optional = true , fetch = FetchType.LAZY)
    Folder folder;

    @OneToMany(fetch = FetchType.LAZY)
    Attach
}
