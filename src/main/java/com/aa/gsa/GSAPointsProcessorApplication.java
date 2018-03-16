package com.aa.gsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.cloudfoundry.CloudFoundryConnector;

@SpringBootApplication(scanBasePackages = {"com.aa.gsa"})
public class GSAPointsProcessorApplication {

	public static void main(String[] args) {
		if (new CloudFoundryConnector().isInMatchingCloud()) {
			System.setProperty("spring.profiles.active", "cloud");
		}
		
		SpringApplication.run(GSAPointsProcessorApplication.class, args);
	}
}
