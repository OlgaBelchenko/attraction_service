package com.example.attraction_service.service;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.entity.Locality;
import org.springframework.stereotype.Service;

/**
 * Сервис, предоставляющий бизнес-логику для действий с сущностью Местоположение ({@link Locality}).
 */

@Service
public interface LocalityService {

    /**
     * Метод для добавления объекта Locality в базу данных.
     *
     * @param localityDto - полученный с front объект класса LocalityDto, предоставляющий данные для добавления
     */
    void addLocality(LocalityDto localityDto);
}
