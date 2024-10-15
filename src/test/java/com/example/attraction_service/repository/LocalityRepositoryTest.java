package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Locality;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
class LocalityRepositoryTest {

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private AttractionRepository attractionRepository;

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
    void testFindByNameIgnoreCaseAndRegionIgnoreCase_whenFound_returnsCorrectAttraction() {
        String name = "Locality";
        String region = "Region";
        Locality locality = localityRepository.save(Locality.builder().name(name).region(region).build());

        Optional<Locality> result = localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase(name.toUpperCase(), region.toLowerCase());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(locality);
    }

    @Test
    void testFindByNameIgnoreCaseAndRegionIgnoreCase_notFound_returnsEmptyOptional() {
        Optional<Locality> result = localityRepository.findByNameIgnoreCaseAndRegionIgnoreCase("Locality", "Region");

        assertThat(result).isNotPresent();
    }
}