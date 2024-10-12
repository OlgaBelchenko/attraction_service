package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Assistance;
import com.example.attraction_service.entity.AssistanceType;
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
class AssistanceRepositoryTest {

    @Autowired
    private AssistanceRepository assistanceRepository;

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
        assistanceRepository.deleteAll();
    }

    @Test
    void testFindByAssistanceTypeAndExecutor_whenFound_returnsCorrectAssistance() {
        AssistanceType assistanceType = AssistanceType.AUTO_TOUR;
        String executor = "someone";
        Assistance assistance = Assistance.builder().assistanceType(assistanceType).executor(executor).build();
        assistanceRepository.save(assistance);

        Optional<Assistance> result = assistanceRepository.findByAssistanceTypeAndExecutor(assistanceType, executor);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(assistance);
    }

    @Test
    void testFindByAssistanceTypeAndExecutor_notFound_returnsEmptyOptional() {
        Optional<Assistance> result = assistanceRepository.findByAssistanceTypeAndExecutor(AssistanceType.AUTO_TOUR, "executor");

        assertThat(result).isNotPresent();
    }
}