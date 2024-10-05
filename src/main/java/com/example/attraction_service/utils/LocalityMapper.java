package com.example.attraction_service.utils;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalityMapper {
    LocalityDto mapToLocalityDto(Locality locality);
    Locality mapToLocality(LocalityDto localityDto);
}
