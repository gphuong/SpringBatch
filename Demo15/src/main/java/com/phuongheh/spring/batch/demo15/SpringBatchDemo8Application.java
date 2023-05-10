package com.phuongheh.spring.batch.demo15;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.phuongheh.spring.batch.demo15"})
public class SpringBatchDemo8Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo8Application.class, args);
	}

}
