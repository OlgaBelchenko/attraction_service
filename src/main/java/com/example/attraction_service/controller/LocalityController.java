package com.example.attraction_service.controller;

import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.response.ErrorResponse;
import com.example.attraction_service.service.LocalityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Местоположение", description = "Создание местоположения")
@RequestMapping("/api/locality")
public class LocalityController {

    private final LocalityService localityService;

    /**
     * Добавление нового местоположения.
     */
    @Operation(summary = "Создание нового местоположения.",
            description = "Создает новое местоположение в базе данных.\n" +
                    "В тело запроса передается объект класса LocalityDto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное создание местоположения"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/add")
    ResponseEntity<Void> addLocality(@RequestBody @Valid LocalityDto localityDto) {
        localityService.addLocality(localityDto);
        return ResponseEntity.ok().build();
    }
}
