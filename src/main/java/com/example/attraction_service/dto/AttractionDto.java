package com.example.attraction_service.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record AttractionDto(
        String name,
        Date created,
        String description,
        String assistanceType,
        String localityName
) {
}
