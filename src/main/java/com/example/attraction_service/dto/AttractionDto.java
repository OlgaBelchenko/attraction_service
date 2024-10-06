package com.example.attraction_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;
import java.util.List;

/**
 * DTO для передачи и получения достопримечательности.
 *
 * @param name - название достопримечательности
 * @param created - дата возникновения достопримечательности
 * @param description - краткое описание достопримечательности
 * @param attractionType - тип достопримечательности
 * @param locality - местоположение достопримечательности
 * @param assistanceDtos - список услуг достопримечательности
 */

@Builder
public record AttractionDto(
        @NotNull
        @NotBlank
        @NotEmpty
        @JsonProperty("name")
        String name,

        @JsonProperty("created")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date created,

        @JsonProperty("description")
        String description,

        @NotNull
        @NotBlank
        @NotEmpty
        @JsonProperty("attraction_type")
        String attractionType,

        @NotNull
        @NotBlank
        @NotEmpty
        @JsonProperty("locality")
        LocalityDto locality,

        @JsonProperty(value = "assistances")
        List<AssistanceDto> assistanceDtos
) {
}
