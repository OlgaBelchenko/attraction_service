package com.example.attraction_service;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AttractionServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AttractionServiceApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
