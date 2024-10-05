package com.example.attraction_service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttractionType {
    CASTLE("Дворец"),
    PARK("Парк"),
    MUSEUM("Музей"),
    RESERVE("Заповедник"),
    ARCHAEOLOGICAL_SITE("Археологический объект");

    private final String value;
}
