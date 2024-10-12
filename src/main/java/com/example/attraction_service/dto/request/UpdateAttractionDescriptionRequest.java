package com.example.attraction_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Запрос на изменение описания достопримечательности.
 *
 * @param name        - название достопримечательности, описание которой необходимо изменить
 * @param description - новое описание достопримечательности
 */

public record UpdateAttractionDescriptionRequest(
        @Schema(description = "Название достопримечательности", defaultValue = "Государственный эрмитаж")
        @NotNull
        @NotBlank
        @JsonProperty("name")
        String name,

        @Schema(description = "Краткое описание достопримечательности", defaultValue = "Российский государственный художественный музей в Санкт-Петербурге")
        @NotNull
        @NotBlank
        @JsonProperty("description")
        String description
) {
}
