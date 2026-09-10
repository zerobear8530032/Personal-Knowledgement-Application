package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SchedulerExpression {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cronExpression;
    private String taskName;
}
