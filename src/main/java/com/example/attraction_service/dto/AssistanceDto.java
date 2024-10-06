package com.example.attraction_service.dto;

import com.example.attraction_service.entity.AssistanceType;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("assistance_type")
        AssistanceType assistanceType,

        @JsonProperty("description")
        String description,

        @JsonProperty("executor")
        String executor
) {
}
