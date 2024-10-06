package com.example.attraction_service.utils;

/**
 * Утилитарный класс для хранения сообщений об ошибках по умолчанию.
 */

public final class DefaultErrorMessages {

    public static final String NO_SUCH_ATTRACTION = "Достопримечательность с названием %s не найдена!";
    public static final String NO_SUCH_ATTRACTION_TYPE = "Тип достопримечательности %s не найден!";
    public static final String ATTRACTION_ALREADY_EXISTS = "Достопримечательность с названием %s уже существует!";
    public static final String LOCALITY_ALREADY_EXISTS = "Местоположение с названием %s уже существует!";

    private DefaultErrorMessages() {
        // Приватный конструктор для невозможности создания объекта утилитарного класса
    }
}
