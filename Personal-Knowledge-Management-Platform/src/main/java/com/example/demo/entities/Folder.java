package com.example.demo.entities;

import com.example.demo.dtos.FolderResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    Long id;
    String name;
    LocalDateTime createAt;
    boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
