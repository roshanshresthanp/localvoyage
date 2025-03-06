package com.example.hotelapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class HotelappApplication {
	private static final Logger logger = LogManager.getLogger(HotelappApplication.class);
	public static void main(String[] args) {
		logger.info("Application is starting...");
		SpringApplication.run(HotelappApplication.class, args);
	}

}
