package com.epam.filmrating.model.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    MUSICAL("Musicals"),
    SCIENCE_FICTION("Sci-fi"),
    WAR("War"),
    WESTERN("Western"),
    DETECTIVE("Detective");

    private final String value;

    Genre(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Genre fromString(String value) {
        Optional<Genre> genreOptional = Arrays.stream(values())
                .filter(genre -> genre.value.equalsIgnoreCase(value))
                .findFirst();
        if (genreOptional.isPresent()) {
            return genreOptional.get();
        } else {
            throw new NoSuchElementException("Can't find such Enum Genre: " + value);
        }
    }


}
