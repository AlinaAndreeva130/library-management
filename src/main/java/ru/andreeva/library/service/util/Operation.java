package ru.andreeva.library.service.util;

import lombok.Getter;

public enum Operation {
    ISSUED("Выдача"),
    REFUND("Возврат"),
    LOSS("Утеря");

    @Getter
    private final String name;

    Operation(String name) {
        this.name = name;
    }
}
