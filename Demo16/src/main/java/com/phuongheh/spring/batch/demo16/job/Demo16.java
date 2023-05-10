package com.phuongheh.spring.batch.demo16.job;

import com.phuongheh.spring.batch.demo16.repo.EmployeeRepo;
import com.phuongheh.spring.batch.demo16.tasklet.DataCleanup;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Demo16 {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EmployeeRepo employeeRepo;

    @Autowired
    public Demo16(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeRepo employeeRepo) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeRepo = employeeRepo;
    }

    @Qualifier(value = "demo16")
    @Bean
    public Job demo16Job() throws Exception {
        return this.jobBuilderFactory.get("demo16")
                .start(step1demo16())
                .build();
    }

    @Bean
    public Step step1demo16() throws Exception {

        return stepBuilderFactory.get("step1")
                .tasklet(new DataCleanup(employeeRepo))
                .build();
    }


}
