package com.phuongheh.spring.batch.demo11.job;

import com.phuongheh.spring.batch.demo11.repo.EmployeeRepo;
import com.phuongheh.spring.batch.demo11.tasklet.AgeGroupSummary;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Demo11 {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeRepo employeeRepo;

    @Autowired
    public Demo11(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeRepo employeeRepo) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeRepo = employeeRepo;
    }

    @Qualifier(value = "demo11")
    @Bean
    public Job demo11Job(){
        return this.jobBuilderFactory.get("demo11")
                .start(step1Demo11())
                .build();
    }

    @Bean
    public Step step1Demo11() {
        return stepBuilderFactory.get("step1")
                .tasklet(new AgeGroupSummary())
                .build();
    }


}
