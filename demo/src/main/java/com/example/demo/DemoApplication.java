package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    @Value("${spring.application.name}")
    private String applicationName;
    
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World from "+applicationName;
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
