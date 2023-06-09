package com.phuongheh.spring.batch.demo16.tasklet;

import com.phuongheh.spring.batch.demo16.repo.EmployeeRepo;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DataCleanup implements Tasklet {

    private EmployeeRepo employeeRepo;

    public DataCleanup(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        employeeRepo.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
