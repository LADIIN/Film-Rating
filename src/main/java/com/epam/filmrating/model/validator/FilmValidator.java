package com.epam.filmrating.model.validator;

import com.epam.filmrating.model.entity.Film;

import java.time.Year;
import java.util.Calendar;

/**
 * Validates {@link Film}.
 */
public class FilmValidator {
    /**
     * Max year.
     */
    private static final Year MAX_YEAR = Year.of(Calendar.getInstance().get(Calendar.YEAR) + 1);
    /**
     * Min year.
     */
    private static final Year MIN_YEAR = Year.of(1895);
    /**
     * Regex of {@link Film} title.
     */
    private static final String TITLE_REGEX = "^[\\p{L}u0-9][\\p{L}u0-9 ,!:\"\\-'()?$]{3,128}$";
    /**
     * Regex of {@link Film} director.
     */
    private static final String DIRECTOR_REGEX = "^[\\p{L}'][ \\p{L}'-.,]{3,128}$";

    /**
     * @param film
     * @return true if {@link Film} is valid and false otherwise.
     */
    public boolean isValid(Film film) {
        return isTitleValid(film.getTitle()) && isDirectorValid(film.getDirector()) && isYearValid(film.getYear());
    }

    /**
     * @param title
     * @return true if title is valid and false otherwise.
     */
    public boolean isTitleValid(String title) {
        return title != null && title.matches(TITLE_REGEX);
    }

    /**
     * @param director
     * @return true if director is valid and false otherwise.
     */
    public boolean isDirectorValid(String director) {
        return director != null && director.matches(DIRECTOR_REGEX);
    }

    /**
     * @param year
     * @return true if year is valid and false otherwise.
     */
    public boolean isYearValid(Year year) {
        return year.isBefore(MAX_YEAR) && year.isAfter(MIN_YEAR);
    }
}
