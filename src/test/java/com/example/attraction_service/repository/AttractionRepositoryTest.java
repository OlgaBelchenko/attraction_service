package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;
import com.example.attraction_service.entity.Locality;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
class AttractionRepositoryTest {

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private LocalityRepository localityRepository;

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
    }

    @Test
    void testExistsByName_whenFound_returnsTrue() {
        Locality locality = localityRepository.save(Locality.builder().name("Locality").region("Region").build());
        String name = "some_name";
        Attraction attraction = Attraction.builder().name(name).created(new Date()).attractionType(AttractionType.CASTLE).locality(locality).build();
        attractionRepository.save(attraction);

        boolean result = attractionRepository.existsByName("some_name");

        assertThat(result).isTrue();
    }

    @Test
    void testExistsByName_notFound_returnsFalse() {
        boolean result = attractionRepository.existsByName("some_name");

        assertThat(result).isFalse();
    }

    @Test
    void testFindByName_whenFound_returnsCorrectAttraction() {
        Locality locality = localityRepository.save(Locality.builder().name("Locality").region("Region").build());
        String name = "some_name";
        Attraction attraction = Attraction.builder().name(name).created(new Date()).attractionType(AttractionType.CASTLE).locality(locality).build();
        attractionRepository.save(attraction);

        Optional<Attraction> result = attractionRepository.findByName("some_name");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(attraction);
    }

    @Test
    void testFindByName_notFound_returnsEmptyOptional() {
        Optional<Attraction> result = attractionRepository.findByName("some_name");

        assertThat(result).isNotPresent();
    }

    @Test
    void testFindByLocality_whenFound_returnsCorrectPage() {
        Locality locality = localityRepository.save(Locality.builder().name("Locality").region("Region").build());
        String name = "some_name";
        Attraction attraction = Attraction.builder().name(name).created(new Date()).attractionType(AttractionType.CASTLE).locality(locality).build();
        attractionRepository.save(attraction);

        Page<Attraction> result = attractionRepository.findByLocality(locality, PageRequest.of(0, 10));

        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(name);
    }

    @Test
    void testFindByLocality_notFound_returnsEmptyPage() {
        Locality locality = localityRepository.save(Locality.builder().name("Locality").region("Region").build());
        Page<Attraction> result = attractionRepository.findByLocality(locality, PageRequest.of(0, 10));

        assertThat(result).isEmpty();
    }

    @Test
    void testFindAllByAttractionType_whenFound_returnsCorrectPage() {
        Locality locality = localityRepository.save(Locality.builder().name("Locality").region("Region").build());
        AttractionType attractionType = AttractionType.CASTLE;
        String name = "some_name";
        Attraction attraction = Attraction.builder().name(name).created(new Date()).attractionType(attractionType).locality(locality).build();
        attractionRepository.save(attraction);

        Page<Attraction> result = attractionRepository.findAllByAttractionType(attractionType, PageRequest.of(0, 10));

        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(name);
    }

    @Test
    void testFindAllByAttractionType_notFound_returnsEmptyLPage() {
        Page<Attraction> result = attractionRepository.findAllByAttractionType(AttractionType.CASTLE, PageRequest.of(0, 10));

        assertThat(result).isEmpty();
    }
}