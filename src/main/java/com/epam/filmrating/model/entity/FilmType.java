package com.epam.filmrating.model.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Film types.
 * @author Darkovich Vladislav
 */
public enum FilmType {
    MOVIE("Movie"),
    SERIES("Series");

    /**
     * String value of enum.
     */
    private final String value;

    /**
     * Constructor.
     * @param value
     */
    FilmType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Converting String value to enum FilmType.
     * @param value
     * @return FilmType.
     */
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
