package com.phuongheh.spring.batch.demo13;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.phuongheh.spring.batch.demo13"})
public class SpringBatchdemo13Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchdemo13Application.class, args);
    }

}
