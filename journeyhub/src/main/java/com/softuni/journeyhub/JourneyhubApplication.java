package com.softuni.journeyhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JourneyhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(JourneyhubApplication.class, args);
	}
}
