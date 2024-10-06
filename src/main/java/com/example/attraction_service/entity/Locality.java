package com.example.attraction_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность "Местоположение" (Locality).
 * Это класс сущности, который представляет таблицу "locality" в базе данных.
 * <p>
 * Класс содержит следующие поля:
 * id - уникальный идентификатор местоположения
 * name - название местоположения
 * region - регион местоположения
 * attractions - список достопримечательностей {@link Attraction}, связанных с этим местоположением
 * <p>
 * Название и регион вместе образуют уникальный ключ.
 */

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "region"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "region")
    private String region;

    @OneToMany(mappedBy = "locality")
    private List<Attraction> attractions;

}
