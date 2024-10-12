package com.example.attraction_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;
import java.util.List;

/**
 * DTO для передачи и получения достопримечательности.
 *
 * @param name           - название достопримечательности
 * @param created        - дата возникновения достопримечательности
 * @param description    - краткое описание достопримечательности
 * @param attractionType - тип достопримечательности
 * @param locality       - местоположение достопримечательности
 * @param assistanceDtos - список услуг достопримечательности
 */

@Builder
public record AttractionDto(
        @Schema(description = "Название достопримечательности", defaultValue = "Исторический сквер")
        @NotNull
        @NotBlank
        @JsonProperty("name")
        String name,

        @Schema(description = "Дата создания достопримечательности", defaultValue = "2020-05-11")
        @JsonProperty("created")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date created,

        @Schema(description = "Краткое описание достопримечательности", defaultValue = "Исторический сквер в центре Екатеринбурга")
        @JsonProperty("description")
        String description,

        @Schema(description = "Тип достопримечательности", allowableValues = "CASTLE, PARK, MUSEUM, RESERVE, ARCHAEOLOGICAL_SITE", defaultValue = "PARK")
        @NotNull
        @NotBlank
        @JsonProperty("attraction_type")
        String attractionType,

        @Schema(description = "Местоположение достопримечательности")
        @NotNull
        @JsonProperty("locality")
        LocalityDto locality,

        @Schema(description = "Услуги достопримечательности")
        @JsonProperty(value = "assistances")
        List<AssistanceDto> assistanceDtos
) {
}
