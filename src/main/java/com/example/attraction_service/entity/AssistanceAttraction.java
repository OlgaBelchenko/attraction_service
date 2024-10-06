package com.example.attraction_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность "Услуга-Достопримечательность" (AssistanceAttraction).
 * Это класс сущности, который представляет связь между услугой {@link Assistance} и достопримечательностью {@link Attraction} в базе данных.
 * <p>
 * Класс содержит следующие поля:
 * assistanceAttractionKey - составной ключ связи между услугой и достопримечательностью
 * assistance - услуга, связанная с достопримечательностью
 * attraction - достопримечательность, связанная с услугой
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistanceAttraction {

    @EmbeddedId
    private AssistanceAttractionKey assistanceAttractionKey;

    @ManyToOne
    @MapsId("assistanceId")
    @JoinColumn(name = "assistance_id")
    private Assistance assistance;

    @ManyToOne
    @MapsId("attractionId")
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
}
