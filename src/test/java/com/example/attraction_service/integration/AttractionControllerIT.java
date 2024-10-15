package com.example.attraction_service.integration;

import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
@Transactional
class AttractionControllerIT {

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private LocalityRepository localityRepository;

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
    void testAddAttraction_success_returnsOk() {
        mockMvc.perform(post("/api/attraction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"attraction\", \"description\": \"description\", \"attraction_type\": \"CASTLE\", \"locality\": {\"region\": \"region\", \"name\": \"locality\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testAddAttraction_attractionAlreadyExists_returnsBadRequest() {
        Locality locality = Locality.builder().name("locality").region("region").build();
        localityRepository.save(locality);
        Attraction attraction = Attraction.builder()
                .name("attraction")
                .description("description")
                .locality(locality)
                .attractionType(AttractionType.CASTLE)
                .build();
        attractionRepository.save(attraction);
        mockMvc.perform(post("/api/attraction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"attraction\", \"description\": \"description\", \"attraction_type\": \"CASTLE\", \"locality\": {\"region\": \"region\", \"name\": \"locality\"}}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testGetAllAttractions_success_returnsOk() {
        mockMvc.perform(get("/api/attraction/list")
                        .param("type", "CASTLE")
                        .param("sort", "name")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testUpdateAttraction_success_returnsNoContent() {
        Locality locality = Locality.builder().name("locality").region("region").build();
        localityRepository.save(locality);
        Attraction attraction = Attraction.builder()
                .name("attraction")
                .description("old description")
                .locality(locality)
                .attractionType(AttractionType.CASTLE)
                .build();
        attractionRepository.save(attraction);

        mockMvc.perform(put("/api/attraction/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"attraction\", \"description\": \"new description\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void testUpdateAttraction_attractionNotFound_returnsNotFound() {
        mockMvc.perform(put("/api/attraction/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"attraction\", \"description\": \"new description\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testDeleteAttraction_success_returnsNoContent() {
        Locality locality = Locality.builder().name("locality").region("region").build();
        localityRepository.save(locality);
        Attraction attraction = Attraction.builder()
                .name("attraction")
                .description("old description")
                .locality(locality)
                .attractionType(AttractionType.CASTLE)
                .build();
        attractionRepository.save(attraction);

        mockMvc.perform(delete("/api/attraction/delete")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("attraction"))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void testDeleteAttraction_attractionNotFound_returnsNotFound() {
        mockMvc.perform(delete("/api/attraction/delete")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("attraction"))
                .andExpect(status().isNotFound());
    }
}
