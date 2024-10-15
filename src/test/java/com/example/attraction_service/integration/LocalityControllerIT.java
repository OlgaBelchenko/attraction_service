package com.example.attraction_service.integration;

import com.example.attraction_service.entity.Locality;
import com.example.attraction_service.repository.AttractionRepository;
import com.example.attraction_service.repository.LocalityRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
@Transactional
class LocalityControllerIT {

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withReuse(true);

    @BeforeAll
    static void setContainer() {
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @AfterAll
    static void stopContainer() {
        container.stop();
    }

    @BeforeEach
    void setUp() {
        attractionRepository.deleteAll();
        localityRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void testAddLocality_success_returnsOK() {
        mockMvc.perform(post("/api/locality/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"name\", \"region\": \"region\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testAddLocality_localityAlreadyExists_returnsBadRequest() {
        Locality locality = Locality.builder().name("locality").region("region").build();
        localityRepository.save(locality);

        mockMvc.perform(post("/api/locality/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"locality\", \"region\": \"region\"}"))
                .andExpect(status().isBadRequest());
    }
}
