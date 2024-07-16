package com.example.OnlineFoodOdering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Model")

public class OnlineFoodOderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineFoodOderingApplication.class, args);
	}

}
