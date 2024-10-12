package com.example.attraction_service.controller;

import com.example.attraction_service.dto.AttractionDto;
import com.example.attraction_service.dto.LocalityDto;
import com.example.attraction_service.dto.request.UpdateAttractionDescriptionRequest;
import com.example.attraction_service.dto.response.ErrorResponse;
import com.example.attraction_service.service.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Достопримечательность", description = "Создание, изменение, удаление, просмотр достопримечательностей")
@RequestMapping("/api/attraction")
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * Создание новой достопримечательности.
     */
    @Operation(summary = "Создание новой достопримечательности.",
            description = "Создает новую достопримечательность в базе данных.\n" +
                    "В тело запроса передается объект класса AttractionDto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное создание достопримечательности"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/add")
    ResponseEntity<Void> addAttraction(@RequestBody @Valid AttractionDto attractionDto) {
        attractionService.addAttraction(attractionDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод возвращает список достопримечательностей.
     * <p>
     * Необязательные параметры запроса:
     *
     * @param attractionType - фильтрация по переданному типу достопримечательности, по умолчанию без фильтрации
     * @param sort           - параметр для сортировки, по умолчанию сортируется по названию достопримечательности
     * @param page           - номер страницы для пагинации, иначе задается сервисом по умолчанию
     * @param size           - количество записей на странице, иначе задается сервисом по умолчанию
     */
    @Operation(summary = "Список достопримечательностей.",
            description = """
                    Возвращает список достопримечательностей.<br>
                    В качестве параметров запроса можно передать:<br>
                        "type" - тип достопримечательности, по которому будет отфильтрован список (допустимые значения: CASTLE, PARK, MUSEUM, RESERVE, ARCHAEOLOGICAL_SITE;<br>
                        "sort" - поле для сортировки списка, по умолчанию список будет отсортирован по названию достопримечательности;<br>
                        "page" - номер страницы для пагинации;<br>
                        "size" - количество записей на странице.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка достопримечательностей"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Не найден тип достопримечательности",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
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
    @Operation(summary = "Список достопримечательностей в определенном местоположении.",
            description = """
                    Возвращает список достопримечательностей.<br>
                    Местоположение задается обязательными параметрами запроса:<br>
                        region - регион местоположения;<br>
                        name - название местоположения.<br>
                    В качестве параметров запроса можно передать:<br>
                        "page" - номер страницы для пагинации;<br>
                        "size" - количество записей на странице.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка достопримечательностей"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено местоположение",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/list/locality")
    ResponseEntity<Page<AttractionDto>> getAllAttractionsByLocality(
            @RequestParam("region") String region,
            @RequestParam("name") String name,
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
    @Operation(summary = "Обновление краткого описания достопримечательности.",
            description = "Обновляет краткое описание достопримечательности.\n" +
                    "В тело запроса передается объект класса UpdateAttractionDescriptionRequest.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Успешное обновление достопримечательности"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Не найдена достопримечательность",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/update")
    ResponseEntity<Void> updateAttraction(@RequestBody @Valid UpdateAttractionDescriptionRequest updateRequest) {
        attractionService.updateAttractionDescription(updateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Удаление достопримечательности по переданному названию.
     */
    @Operation(summary = "Удаление достопримечательности.",
            description = "Удаляет достопримечательность по переданному названию.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Успешное удаление достопримечательности"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Не найдена достопримечательность",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteAttraction(@RequestBody String name) {
        attractionService.deleteAttraction(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
