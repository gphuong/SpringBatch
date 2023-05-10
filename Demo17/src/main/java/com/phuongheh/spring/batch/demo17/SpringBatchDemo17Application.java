package com.phuongheh.spring.batch.demo17;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@ComponentScan(basePackages = {"com.phuongheh.spring.batch.demo17"})
public class SpringBatchDemo17Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDemo17Application.class, args);
    }

}
