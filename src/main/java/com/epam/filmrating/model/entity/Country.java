package com.epam.filmrating.model.entity;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum Country {
    USA("USA"),
    JAPAN("Japan"),
    UK("UK"),
    GERMANY("Germany"),
    AUSTRIA("Austria"),
    BELARUS("Belarus"),
    BRAZIL("Brazil"),
    CANADA("Canada"),
    CHINA("China"),
    DENMARK("Denmark"),
    FINLAND("Finland"),
    GREECE("Greece"),
    ICELAND("Iceland"),
    INDIA("India"),
    ITALY("Italy"),
    MEXICO("Mexico"),
    NETHERLANDS("Netherlands"),
    NEW_ZEALAND("New-Zealand"),
    NORWAY("Norway"),
    POLAND("Poland"),
    PORTUGAL("Portugal"),
    RUSSIA("Russia"),
    SINGAPORE("Singapore"),
    SPAIN("Spain"),
    SWEDEN("Sweden"),
    SWITZERLAND("Switzerland"),
    TURKEY("Turkey"),
    UKRAINE("Ukraine");


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
