package com.timetablegenerator.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScans(@ComponentScan(basePackages = {"com.timetablegenerator.algorithm","com.timetablegenerator.service"}))
@EntityScan(basePackages = {"com.timetablegenerator.entity"})  
@EnableJpaRepositories(basePackages = {"com.timetablegenerator.repository"})
@SpringBootApplication(scanBasePackages = {"com.timetablegenerator.controller","com.timetablegenerator.restcontroller"})
public class TimeTableGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTableGeneratorApplication.class, args);
	}

}
  