package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;
import com.example.attraction_service.entity.Locality;

public final class AttractionMapper {

    private AttractionMapper() {
    }

    public static Attraction mapToAttraction(AttractionDto attractionDto) {
        Locality locality = LocalityMapper.mapToLocality(attractionDto.locality());
        AttractionType attractionType = AttractionType.valueOf(attractionDto.attractionType());
        return Attraction.builder()
                .name(attractionDto.name())
                .created(attractionDto.created())
                .description(attractionDto.description())
                .attractionType(attractionType)
                .locality(locality)
                .build();

    }

    public static AttractionDto mapToAttractionDto(Attraction attraction) {
        return AttractionDto.builder()
                .name(attraction.getName())
                .created(attraction.getCreated())
                .description(attraction.getDescription())
                .attractionType(attraction.getAttractionType().getValue())
                .locality(LocalityMapper.mapToLocalityDto(attraction.getLocality()))
                .build();
    }


}
