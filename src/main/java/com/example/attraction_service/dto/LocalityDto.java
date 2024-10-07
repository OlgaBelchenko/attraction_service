package com.example.attraction_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для передачи и получения местоположения.
 *
 * @param name - название достопримечательности
 * @param region - регион достопримечательности
 */

public record LocalityDto(
        @Schema(description = "Название местоположения", defaultValue = "Санкт-Петербург")
        @NotBlank
        @NotNull
        @JsonProperty("name")
        String name,

        @Schema(description = "Регион", defaultValue = "Ленинградская область")
        @NotNull
        @NotBlank
        @JsonProperty("region")
        String region
) {
}
