package com.phuongheh.batch.demo1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchDemo1Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDemo1Application.class, args);
    }
}
