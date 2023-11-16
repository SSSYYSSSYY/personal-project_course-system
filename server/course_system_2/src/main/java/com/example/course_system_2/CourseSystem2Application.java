package com.example.course_system_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CourseSystem2Application {

	public static void main(String[] args) {
		SpringApplication.run(CourseSystem2Application.class, args);
	}

}
