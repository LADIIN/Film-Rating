package com.epam.filmrating.model.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum Country {
    USA("USA");

    private final String value;

    Country(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Country fromString(String value) {
        Optional<Country> countryOptional = Arrays.stream(values())
                .filter(genre -> genre.value.equalsIgnoreCase(value))
                .findFirst();
        if (countryOptional.isPresent()) {
            return countryOptional.get();
        } else {
            throw new NoSuchElementException("Can't find such Enum Country:" + value);
        }
    }
}
