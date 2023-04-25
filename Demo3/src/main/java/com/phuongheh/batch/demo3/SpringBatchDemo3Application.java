package com.phuongheh.batch.demo3;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchDemo3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo3Application.class, args);
	}

}
