package com.phuongheh.spring.batch.demo17.job;

import com.phuongheh.spring.batch.demo17.repo.EmployeeRepo;
import com.phuongheh.spring.batch.demo17.tasklet.AgeGroupSummary;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Demo17 {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeRepo employeeRepo;

    @Autowired
    public Demo17(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeRepo employeeRepo) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeRepo = employeeRepo;
    }

    @Qualifier(value = "demo17")
    @Bean
    public Job demo17Job() throws Exception {
        return this.jobBuilderFactory.get("demo17")
                .start(step1Demo17())
                .build();
    }

    @Bean
    public Step step1Demo17() throws Exception {

        return stepBuilderFactory.get("step1")
                .tasklet(new AgeGroupSummary())
                .build();
    }
}
