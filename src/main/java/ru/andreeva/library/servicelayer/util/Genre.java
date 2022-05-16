package ru.andreeva.library.servicelayer.util;

import lombok.Getter;

public enum Genre {
    LEARN("Учебная литература"),
    NOVEL("Роман");

    @Getter
    private final String name;

    Genre(String name) {
        this.name = name;
    }
}
