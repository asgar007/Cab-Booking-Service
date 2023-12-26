package com.assignment.cabservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SpringCabServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringCabServiceApplication.class, args);
		System.out.println("App started!");
	}

}
