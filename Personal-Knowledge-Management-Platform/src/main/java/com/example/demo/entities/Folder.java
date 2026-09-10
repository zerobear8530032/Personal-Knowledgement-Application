package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(exclude = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDateTime createAt;
    private boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
