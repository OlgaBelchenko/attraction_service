package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;

/**
 * Класс для маппинга объектов класса {@link LocalityDto} в объекты класса {@link Locality} и наоборот.
 */

public final class LocalityMapper {

    private LocalityMapper() {
        // Приватный конструктор для невозможности создания объекта утилитарного класса
    }

    /**
     * Метод для маппинга объектов Locality в объекты LocalityDto.
     *
     * @param locality - объект класса Locality для маппинга
     * @return возвращает новый объект класса LocalityDto
     */
    public static LocalityDto mapToLocalityDto(Locality locality) {
        return new LocalityDto(locality.getName(), locality.getRegion());
    }

    /**
     * Метод для маппинга объектов LocalityDto в объекты Locality.
     *
     * @param localityDto - объект класса LocalityDto для маппинга
     * @return возвращает новый объект класса Locality
     */
    public static Locality mapToLocality(LocalityDto localityDto) {
        return Locality.builder()
                .name(localityDto.name())
                .region(localityDto.region())
                .build();
    }
}
