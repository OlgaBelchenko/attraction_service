package com.example.attraction_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для передачи и получения местоположения.
 *
 * @param name - название достопримечательности
 * @param region - регион достопримечательности
 */

public record LocalityDto(
        @NotBlank
        @NotNull
        @JsonProperty("name")
        String name,
        @NotNull
        @NotBlank
        @JsonProperty("region")
        String region
) {
}
