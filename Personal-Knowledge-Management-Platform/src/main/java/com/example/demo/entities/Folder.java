package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    LocalDateTime updateAt;
    boolean isDeleted;
}
