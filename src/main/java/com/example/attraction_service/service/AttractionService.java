package com.example.attraction_service.service;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.entity.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис, предоставляющий бизнес-логику для действий с сущностью Достопримечательность ({@link Attraction}).
 */

@Service
public interface AttractionService {

    /**
     * Метод для добавления объекта Attraction в базу данных.
     *
     * @param attractionDto - полученный с front объект класса AttractionDto, предоставляющий данные для добавления
     */
    void addAttraction(AttractionDto attractionDto);

    /**
     * Получение списка объектов Attraction из базы данных.
     * <p>
     *
     * @param attractionType - выборка по типу достопримечательности
     * @param sort           - поле сортировки выборки
     * @param page           - номер страницы для пагинации
     * @param size           - число записей на странице
     * @return возвращает список объектов класса AttractionDto для передачи на front
     */
    Page<AttractionDto> getAllAttractions(Optional<String> attractionType, Optional<String> sort, Optional<Integer> page, Optional<Integer> size);

    /**
     * Метод для получения всех достопримечательностей из базы данных из определенного местоположения.
     * <p>
     *
     * @param localityDto - объект класса LocalityDto, предоставляющий информацию о местоположении,
     *                    по которому необходимо сделать выборку
     * @param page        - номер страницы для пагинации
     * @param size        - число записей на странице
     * @return возвращает список объектов класса AttractionDto для передачи на front
     */
    Page<AttractionDto> getAllAttractionsByLocality(LocalityDto localityDto, Optional<Integer> page, Optional<Integer> size);

    /**
     * Метод для обновления данных о существующем в базе данных объекте класса Attraction.
     * Возможно обновление только поля description.
     *
     * @param updateRequest - запрос на обновление достопримечательности,
     *                      содержит имя достопримечательности и новое описание
     */
    void updateAttractionDescription(UpdateAttractionDescriptionRequest updateRequest);

    /**
     * Метод для удаления из базы данных объекта класса Attraction.
     *
     * @param name - имя достопримечательности, которую нужно удалить
     */
    void deleteAttraction(String name);
}
