package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.AttractionType;

/**
 * Класс для маппинга объектов класса {@link AttractionDto} в объекты класса {@link Attraction} и наоборот.
 */

public final class AttractionMapper {

    private AttractionMapper() {
        // Приватный конструктор для невозможности создания объекта утилитарного класса
    }

    /**
     * Метод для маппинга объектов AttractionDto в объекты Attraction.
     *
     * @param attractionDto - объект класса AttractionDto для маппинга
     * @return возвращает новый объект класса Attraction
     */
    public static Attraction mapToAttraction(AttractionDto attractionDto) {
        AttractionType attractionType = AttractionType.valueOf(attractionDto.attractionType().toUpperCase());
        return Attraction.builder()
                .name(attractionDto.name())
                .created(attractionDto.created())
                .description(attractionDto.description())
                .attractionType(attractionType)
                .build();

    }

    /**
     * Метод для маппинга объектов Attraction в объекты AttractionDto.
     *
     * @param attraction - объект класса Attraction для маппинга
     * @return возвращает новый объект класса AttractionDto
     */
    public static AttractionDto mapToAttractionDto(Attraction attraction) {
        return AttractionDto.builder()
                .name(attraction.getName())
                .created(attraction.getCreated())
                .description(attraction.getDescription())
                .attractionType(attraction.getAttractionType().name())
                .locality(LocalityMapper.mapToLocalityDto(attraction.getLocality()))
                .assistanceDtos(attraction.getAssistances().stream().map(AssistanceMapper::mapToAssistanceDto).toList())
                .build();
    }
}
