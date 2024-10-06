package com.example.attraction_service.service;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.entity.Attraction;
import org.springframework.stereotype.Service;

import java.util.List;
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
     *
     * @param sortByName     - необязательный параметр, признак сортировки списка по имени
     * @param attractionType - необязательный параметр, передающий выборку по типу достопримечательности
     * @return возвращает список объектов класса AttractionDto для передачи на front
     */
    List<AttractionDto> getAllAttractions(Optional<Boolean> sortByName, Optional<String> attractionType);

    /**
     * Метод для получения всех достопримечательностей из базы данных из определенного местоположения.
     *
     * @param localityDto - объект класса LocalityDto, предоставляющий информацию о местоположении,
     *                    по которому необходимо сделать выборку
     * @return возвращает список объектов класса AttractionDto для передачи на front
     */
    List<AttractionDto> getAllAttractionsByLocality(LocalityDto localityDto);

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
