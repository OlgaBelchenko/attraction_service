package com.example.attraction_service.dto;

import com.example.attraction_service.entity.AttractionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttractionsRequest {
    private boolean sortByName;
    AttractionType attractionType;
}
