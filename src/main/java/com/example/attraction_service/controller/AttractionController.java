package com.example.attraction_service.controller;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.service.AttractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * <p>
     * Необязательные параметры запроса:
     *
     * @param attractionType - фильтрация по переданному типу достопримечательности, по умолчанию без фильтрации
     * @param sort           - параметр для сортировки, по умолчанию сортируется по названию достопримечательности
     * @param page           - номер страницы для пагинации, иначе задается сервисом по умолчанию
     * @param size           - количество записей на странице, иначе задается сервисом по умолчанию
     */
    @GetMapping("/list")
    public ResponseEntity<Page<AttractionDto>> getAllAttractions(
            @RequestParam("type") Optional<String> attractionType,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        return ResponseEntity.ok(attractionService.getAllAttractions(attractionType, sort, page, size));
    }

    /**
     * Метод возвращает список достопримечательностей по переданного с front местоположения.
     * <p>
     * Параметры ссылки:
     *
     * @param region - регион местоположения
     * @param name   - название местоположения
     *               <p>
     *               Необязательные параметры:
     * @param page   - номер страницы для пагинации, иначе задается сервисом по умолчанию
     * @param size   - количество записей на странице, иначе задается сервисом по умолчанию
     */
    @GetMapping("/{region}/{name}")
    ResponseEntity<Page<AttractionDto>> getAllAttractionsByLocality(
            @PathVariable String region,
            @PathVariable String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        if (region == null || name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LocalityDto localityDto = new LocalityDto(region, name);
        return ResponseEntity.ok(attractionService.getAllAttractionsByLocality(localityDto, page, size));
    }

    /**
     * Обновление краткого описания достопримечательности.
     *
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
