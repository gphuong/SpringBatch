package com.phuongheh.spring.batch.demo12;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.phuongheh.spring.batch.demo12"})
public class SpringBatchdemo12Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchdemo12Application.class, args);
	}

}
