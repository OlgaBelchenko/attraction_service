package com.example.attraction_service.controller;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.service.AttractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с достопримечательностями.
 * Является REST-контроллером, который обрабатывает запросы на добавление, получение, обновление и удаление достопримечательностей.
 * Контроллер использует сервис {@link AttractionService} для выполнения бизнес-логики.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attraction")
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * Добавление новой достопримечательности.
     */
    @PostMapping("/add")
    ResponseEntity<Void> addAttraction(@RequestBody AttractionDto attractionDto) {
        attractionService.addAttraction(attractionDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод возвращает список всех достопримечательностей.
     * Есть возможность получить параметры запроса:
     * @param sortByName - сортировка списка по названию
     * @param attractionType - фильтрация по переданному типу достопримечательности
     */
    @GetMapping("/list")
    ResponseEntity<List<AttractionDto>> getAllAttractions(
            @RequestParam("sort") Optional<Boolean> sortByName,
            @RequestParam("type") Optional<String> attractionType) {
        return ResponseEntity.ok(attractionService.getAllAttractions(sortByName, attractionType));
    }

    /**
     * Метод возвращает список достопримечательностей по переданного с front местоположения.
     * @param region - регион местоположения
     * @param name - название местоположения
     */
    @GetMapping("/{region}/{name}")
    ResponseEntity<List<AttractionDto>> getAllAttractionsByLocality(@PathVariable String region, @PathVariable String name) {
        if (region == null || name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LocalityDto localityDto = new LocalityDto(region, name);
        ;
        return ResponseEntity.ok(attractionService.getAllAttractionsByLocality(localityDto));
    }

    /**
     * Обновление краткого описания достопримечательности.
     * @param updateRequest содержит название достопримечательности и новое краткое описание.
     */
    @PutMapping("/update")
    ResponseEntity<Void> updateAttraction(@RequestBody @Valid UpdateAttractionDescriptionRequest updateRequest) {
        attractionService.updateAttractionDescription(updateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Удаление достопримечательности по переданному названию.
     */
    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteAttraction(@RequestBody String name) {
        attractionService.deleteAttraction(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
