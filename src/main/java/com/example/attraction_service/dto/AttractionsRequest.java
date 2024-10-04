package com.example.attraction_service.dto;

import com.example.attraction_service.entity.AttractionType;
import lombok.Builder;

@Builder
public record AttractionsRequest(
        boolean sortByName,
        AttractionType attractionType
) {

}
