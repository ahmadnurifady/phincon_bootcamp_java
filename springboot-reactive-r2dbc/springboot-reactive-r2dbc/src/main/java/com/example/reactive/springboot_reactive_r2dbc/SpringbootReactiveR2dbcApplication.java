package com.example.reactive.springboot_reactive_r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;

import io.r2dbc.spi.ConnectionFactory;

/* this annotation is not necessary because
 * R2DBC repository support is enabled in our 
 * Spring Boot application by default*/
@EnableR2dbcRepositories
@EnableWebFlux
@SpringBootApplication
public class SpringbootReactiveR2dbcApplication {

//    @Bean
//    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//
//        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
//
//        return initializer;
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootReactiveR2dbcApplication.class, args);
    }

}
