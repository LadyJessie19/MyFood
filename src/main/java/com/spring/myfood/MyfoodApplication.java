package com.spring.myfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.spring.myfood.config", "com.spring.myfood.controller", "com.spring.myfood.service",
		"com.spring.myfood.model",
		"com.spring.myfood.repository", "com.spring.myfood.enums", "com.spring.myfood.mongo" })
public class MyfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfoodApplication.class, args);
	}

}