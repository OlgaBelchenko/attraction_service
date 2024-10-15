package com.example.attraction_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Класс для возвращения ответа на фронт в случае возникновения ошибки.
 */

public record ErrorResponse(
        @Schema(description = "Текст сообщения об ошибке")
        String response
) {
}
