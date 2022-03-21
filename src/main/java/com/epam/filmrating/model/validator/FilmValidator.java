package com.epam.filmrating.model.validator;

import com.epam.filmrating.model.entity.Film;

import java.time.Year;
import java.util.Calendar;

public class FilmValidator {
    private static final int MAX_LENGTH = 128;
    private static final Year MAX_YEAR = Year.of(Calendar.getInstance().get(Calendar.YEAR) + 1);
    private static final Year MIN_YEAR = Year.of(1895);

    public boolean isValid(Film film) {
        return isTitleValid(film.getTitle()) && isDirectorValid(film.getDirector()) && isYearValid(film.getYear());
    }

    private boolean isTitleValid(String title) {
        return title != null && !title.isEmpty() && title.length() < MAX_LENGTH && !title.trim().isEmpty();
    }

    private boolean isDirectorValid(String director) {
        return director != null && !director.isEmpty() && director.length() < MAX_LENGTH && !director.trim().isEmpty();
    }

    private boolean isYearValid(Year year) {
        return year.isBefore(MAX_YEAR) && year.isAfter(MIN_YEAR);
    }
}
