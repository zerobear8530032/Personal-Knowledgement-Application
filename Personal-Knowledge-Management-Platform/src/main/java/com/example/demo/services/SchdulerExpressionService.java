package com.example.demo.services;

import com.example.demo.dtos.CreateSchedulerExpressionRequest;
import com.example.demo.dtos.SchedulerExpressionResponse;
import com.example.demo.dtos.UpdateSchedulerExpressionRequest;
import com.example.demo.entities.SchedulerExpression;
import com.example.demo.exceptions.SchedulerNotFoundException;
import com.example.demo.mappers.SchedulerExpressionMapper;
import com.example.demo.repositories.SchedulerExpressionRepository;
import com.example.demo.scheduler.CleanUpScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchdulerExpressionService {

    private final SchedulerExpressionRepository schedulerExpressionRepository;
    private final SchedulerExpressionMapper schedulerExpressionMapper;
    private final TaskScheduler taskScheduler;
    private final CleanUpScheduler cleanUpScheduler;

    @Autowired
    public SchdulerExpressionService(SchedulerExpressionRepository schedulerExpressionRepository, SchedulerExpressionMapper schedulerExpressionMapper, TaskScheduler taskScheduler, CleanUpScheduler cleanUpScheduler) {
        this.schedulerExpressionRepository = schedulerExpressionRepository;
        this.schedulerExpressionMapper = schedulerExpressionMapper;
        this.taskScheduler = taskScheduler;
        this.cleanUpScheduler = cleanUpScheduler;
    }

    public void startScheduler(Long id) {

        SchedulerExpression schedulerExpression =
                schedulerExpressionRepository.findById(id)
                        .orElseThrow(() ->
                                new SchedulerNotFoundException("Expression Not found"));
        String cronExpression = schedulerExpression.getCronExpression();

        taskScheduler.schedule(
                () -> {
                    System.out.println(
                            "Executing task: " +
                                    schedulerExpression.getTaskName()
                    );
                },
                new CronTrigger(cronExpression)
        );
    }

    public List<SchedulerExpressionResponse> getAllCronsExpressions(){
        return schedulerExpressionRepository.findAll().stream().map(e->schedulerExpressionMapper.schedulerExpressionEntityToSchedulerExpressionResponse(e)).toList();
    }
    public SchedulerExpressionResponse getCronsExpressionById(Long id){
        SchedulerExpression schedulerExpression= schedulerExpressionRepository.findById(id).orElseThrow(()-> new SchedulerNotFoundException("Expression Not found"));
        return schedulerExpressionMapper.schedulerExpressionEntityToSchedulerExpressionResponse(schedulerExpression);
    }

    public SchedulerExpressionResponse createCronExpression(CreateSchedulerExpressionRequest createSchedulerExpressionRequest){
        SchedulerExpression schedulerExpression= new SchedulerExpression();
        schedulerExpression.setTaskName(createSchedulerExpressionRequest.getTaskName());
        schedulerExpression.setCronExpression(createSchedulerExpressionRequest.getCronExpression());
        SchedulerExpression savedExpression = schedulerExpressionRepository.save(schedulerExpression);
        return  schedulerExpressionMapper.schedulerExpressionEntityToSchedulerExpressionResponse(savedExpression);
    }
    public SchedulerExpressionResponse updateCronExpression(UpdateSchedulerExpressionRequest updateSchedulerExpressionRequest){
        SchedulerExpression schedulerExpression= schedulerExpressionRepository.findById(updateSchedulerExpressionRequest.getId()).orElseThrow(()-> new SchedulerNotFoundException("Expression Not found"));
        schedulerExpression.setCronExpression(updateSchedulerExpressionRequest.getCronExpression());
        schedulerExpression.setTaskName(updateSchedulerExpressionRequest.getTaskName());
        SchedulerExpression expression=schedulerExpressionRepository.save(schedulerExpression);
        return schedulerExpressionMapper.schedulerExpressionEntityToSchedulerExpressionResponse(expression);
    }
}
