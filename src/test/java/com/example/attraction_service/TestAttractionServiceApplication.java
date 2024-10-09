package com.example.attraction_service;

import org.springframework.boot.SpringApplication;

public class TestAttractionServiceApplication {

    // todo

    public static void main(String[] args) {
        SpringApplication.from(AttractionServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
