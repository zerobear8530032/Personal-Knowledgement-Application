package com.example.demo.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchedulerExpressionRequest {
    @NotBlank
    String cronExpression;
    @NotBlank
    String taskName;
}
