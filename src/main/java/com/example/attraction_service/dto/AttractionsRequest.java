package com.example.attraction_service.dto;

import lombok.Builder;

@Builder
public record AttractionsRequest(
        boolean sortByName,
        String attractionType
) {
}
