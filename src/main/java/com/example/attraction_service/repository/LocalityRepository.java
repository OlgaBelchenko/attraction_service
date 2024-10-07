package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Locality;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Locality}.
 */

@Registered
public interface LocalityRepository extends JpaRepository<Locality, Long> {

    /**
     * Метод для поиска местоположения в базе данных с переданными названием и регионом,
     * независимо от регистра.
     *
     * @param name - название местоположения для поиска в базе данных
     * @param region - регион местоположения для поиска в базе данных
     * @return возвращает список местоположений в соответствии с заданными параметрами
     */
    Optional<Locality> findByNameAndRegionIgnoreCase(String name, String region);
}
