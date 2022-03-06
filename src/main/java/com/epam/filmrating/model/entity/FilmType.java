package com.epam.filmrating.model.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum FilmType {
    MOVIE("Movie"),
    SERIES("Series"),
    CARTOON("Cartoon");

    private final String value;

    FilmType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static FilmType fromString(String value) {
        Optional<FilmType> filmTypeOptional = Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst();
        if (filmTypeOptional.isPresent()) {
            return filmTypeOptional.get();
        } else {
            throw new NoSuchElementException("Can't find such Enum Film: " + value);
        }
    }
}
