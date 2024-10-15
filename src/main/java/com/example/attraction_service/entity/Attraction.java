package com.example.attraction_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Сущность "Достопримечательность" (Attraction).
 * Это класс сущности, который представляет таблицу attraction в базе данных.
 * <p>
 * Класс содержит следующие поля:
 * id - уникальный идентификатор достопримечательности
 * name - название достопримечательности (уникальное)
 * created - дата появления достопримечательности
 * description - краткое описание достопримечательности
 * attractionType - тип достопримечательности {@link AttractionType}
 * locality - местоположение достопримечательности {@link Locality}
 * assistances - список услуг {@link Assistance}, связанных с этой достопримечательностью
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "description")
    private String description;

    @Column(name = "attraction_type")
    @Enumerated(EnumType.STRING)
    private AttractionType attractionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @ManyToMany
    @JoinTable(
            name = "assistance_attraction",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "assistance_id")
    )
    private List<Assistance> assistances;
}
