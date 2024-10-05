package com.example.attraction_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssistanceType {
    GUIDE("Гид"),
    AUTO_TOUR("Автоэкскурсия"),
    CATERING("Питание");

    private final String value;
}
