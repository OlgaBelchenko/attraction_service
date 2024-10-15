package com.example.attraction_service.utils.mapper;

import com.example.attraction_service.dto.AssistanceDto;
import com.example.attraction_service.entity.Assistance;

/**
 * Класс для маппинга объектов класса {@link AssistanceDto} в объекты класса {@link Assistance} и наоборот.
 */

public class AssistanceMapper {
    private AssistanceMapper() {
        // Приватный конструктор для невозможности создания объекта утилитарного класса
    }

    /**
     * Метод для маппинга объектов Assistance в объекты AssistanceDto.
     *
     * @param assistance - объект класса Locality для маппинга
     * @return возвращает новый объект класса LocalityDto
     */
    public static AssistanceDto mapToAssistanceDto(Assistance assistance) {
        return AssistanceDto.builder()
                .assistanceType(assistance.getAssistanceType())
                .description(assistance.getDescription())
                .executor(assistance.getExecutor())
                .build();
    }

    /**
     * Метод для маппинга объектов AssistanceDto в объекты Assistance.
     *
     * @param assistanceDto - объект класса LocalityDto для маппинга
     * @return возвращает новый объект класса Locality
     */
    public static Assistance mapToAssistance(AssistanceDto assistanceDto) {
        return Assistance.builder()
                .description(assistanceDto.description())
                .assistanceType(assistanceDto.assistanceType())
                .executor(assistanceDto.executor())
                .build();
    }

}
