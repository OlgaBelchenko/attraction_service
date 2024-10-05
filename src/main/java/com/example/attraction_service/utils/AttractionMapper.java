package com.example.attraction_service.utils;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.entity.Attraction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttractionMapper {
    Attraction mapToAttraction(AttractionDto attractionDto);
    AttractionDto mapToAttractionDto(Attraction attraction);
}
