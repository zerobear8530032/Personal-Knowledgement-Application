package com.example.demo.mappers;

import com.example.demo.dtos.SchedulerExpressionResponse;
import com.example.demo.dtos.UpdateSchedulerExpressionRequest;
import com.example.demo.entities.SchedulerExpression;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchedulerExpressionMapper {
    public SchedulerExpressionResponse schedulerExpressionEntityToSchedulerExpressionResponse(SchedulerExpression schedulerExpression);
    public SchedulerExpression updateSchedulerExpressionRequestToSchedulerExpressionEntity(UpdateSchedulerExpressionRequest updateSchedulerExpressionRequest);
}
