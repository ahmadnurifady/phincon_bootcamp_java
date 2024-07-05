package com.example.demomaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @ComponentScan()
// @EntityScan()
@EnableJpaRepositories("com.example.demomaven.repo")
@SpringBootApplication
public class DemomavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemomavenApplication.class, args);
	}

}
