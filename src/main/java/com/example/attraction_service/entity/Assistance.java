package com.example.attraction_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность "Услуга" (Assistance).
 * Это класс сущности, который представляет таблицу "assistance" в базе данных.
 * <p>
 * Класс содержит следующие поля:
 * id - уникальный идентификатор услуги
 * assistanceType - тип услуги {@link AssistanceType}
 * description - краткое описание услуги
 * executor - исполнитель услуги
 * attractions - список достопримечательностей, связанных с этой услугой {@link Attraction}
 */

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"assistance_type", "executor"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assistance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssistanceType assistanceType;

    @Column(name = "description")
    private String description;

    @Column(name = "executor", nullable = false)
    private String executor;

    @ManyToMany(mappedBy = "assistances")
    private List<Attraction> attractions;
}
