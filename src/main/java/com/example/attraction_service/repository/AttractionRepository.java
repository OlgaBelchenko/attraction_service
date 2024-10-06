package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Attraction;
import com.example.attraction_service.entity.Locality;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Attraction}.
 */

@Registered
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    /**
     * Метод для проверки существования записи в базе данных с переданным названием достопримечательности.
     *
     * @param name - название достопримечательности для поиска в базе данных
     * @return возвращает true, если такая достопримечательность существует, false - если не существует
     */
    boolean existsByName(String name);

    /**
     * Метод для проверки существования записи в базе данных с переданным названием достопримечательности.
     *
     * @param name - название достопримечательности для поиска в базе данных
     * @return возвращает true, если такая достопримечательность существует, false - если не существует
     */
    Optional<Attraction> findByName(String name);

    /**
     * Метод для получения списка достопримечательностей в переданном местоположении.
     *
     * @param locality - объект местоположения для поиска в базе данных
     * @return возвращает список достопримечательностей в переданном местоположении
     */
    List<Attraction> findByLocality(Locality locality);
}
