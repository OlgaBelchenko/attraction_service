package com.example.attraction_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LocalityDto(
        @JsonProperty("name")
        String name,
        @JsonProperty("region")
        String region
) {
}
