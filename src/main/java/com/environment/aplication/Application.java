package com.environment.aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

// ONU018
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}

