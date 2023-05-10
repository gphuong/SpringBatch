package com.phuongheh.spring.batch.demo14;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.phuongheh.spring.batch.demo14"})
public class SpringBatchdemo14Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchdemo14Application.class, args);
	}

}
