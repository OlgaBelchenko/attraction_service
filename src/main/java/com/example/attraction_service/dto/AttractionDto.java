package com.example.attraction_service.dto;

import com.example.attraction_service.entity.Locality;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;

@Builder
public record AttractionDto(
        @JsonProperty("name")
        String name,

        @JsonProperty("created")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date created,

        @JsonProperty("description")
        String description,

        @JsonProperty("attraction_type")
        String attractionType,

        @JsonProperty("locality")
        LocalityDto locality
) {
}
