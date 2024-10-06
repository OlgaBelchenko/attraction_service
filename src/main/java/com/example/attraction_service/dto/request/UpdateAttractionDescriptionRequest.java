package com.example.attraction_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Запрос на изменение описания достопримечательности.
 *
 * @param name - название достопримечательности, описание которой необходимо изменить
 * @param description - новое описание достопримечательности
 */

public record UpdateAttractionDescriptionRequest(
        @NotNull
        @NotBlank
        @NotEmpty
        @JsonProperty("name")
        String name,

        @NotNull
        @NotBlank
        @NotEmpty
        @JsonProperty("description")
        String description
) {
}
