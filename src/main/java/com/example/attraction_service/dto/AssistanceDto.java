package com.example.attraction_service.dto;

import com.example.attraction_service.entity.AssistanceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * DTO для получения и передачи услуги.
 *
 * @param assistanceType - тип услуги
 * @param description - описание услуги
 * @param executor - исполнитель услуги
 */

@Builder
public record AssistanceDto(
        @Schema(description = "Тип услуги", defaultValue = "GUIDE")
        @JsonProperty("assistance_type")
        AssistanceType assistanceType,

        @Schema(description = "Краткое описание услуги", defaultValue = "Гид ИП Иванов И.И.")
        @JsonProperty("description")
        String description,

        @Schema(description = "Исполнитель услуги", defaultValue = "Иванов И.И.")
        @JsonProperty("executor")
        String executor
) {
}
