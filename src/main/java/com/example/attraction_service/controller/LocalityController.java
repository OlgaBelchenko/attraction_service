package com.example.attraction_service.controller;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.service.LocalityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с местоположениями.
 * Является REST-контроллером, который обрабатывает запросы на добавление местоположений.
 * Контроллер использует сервис {@link LocalityService} для выполнения бизнес-логики.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locality")
public class LocalityController {

    private final LocalityService localityService;

    /**
     * Добавление нового местоположения.
     */
    @PostMapping("/add")
    ResponseEntity<Void> addLocality(@RequestBody @Valid LocalityDto localityDto) {
        localityService.addLocality(localityDto);
        return ResponseEntity.ok().build();
    }
}
