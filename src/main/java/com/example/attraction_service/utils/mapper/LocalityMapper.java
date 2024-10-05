package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;

public final class LocalityMapper {

    private LocalityMapper() {
    }

    public static LocalityDto mapToLocalityDto(Locality locality) {
        return LocalityDto.builder()
                .name(locality.getName())
                .region(locality.getRegion())
                .build();
    }

    public static Locality mapToLocality(LocalityDto localityDto) {
        return Locality.builder()
                .name(localityDto.name())
                .region(localityDto.region())
                .build();
    }


}
