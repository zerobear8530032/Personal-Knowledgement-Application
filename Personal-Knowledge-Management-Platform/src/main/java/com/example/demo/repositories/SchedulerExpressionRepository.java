package com.example.demo.repositories;

import com.example.demo.entities.SchedulerExpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerExpressionRepository extends JpaRepository<SchedulerExpression,Long> {
}
