package com.epam.filmrating.model.validator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Year;
import java.util.stream.Stream;

public class FilmValidatorTest {
    private static final Year CORRECT_YEAR = Year.of(2014);
    public static final Year INCORRECT_YEAR = Year.of(5055);

    private static Stream<String> provideValidTitles() {
        return Stream.of(
                "Dune",
                "Dune: Chapter One",
                "Дюна",
                "Dune 2"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with title = {0}")
    @MethodSource("provideValidTitles")
    public void testIsTitleValidShouldReturnTrueWhenIsCorrect(String title) {
        //given
        FilmValidator filmValidator = new FilmValidator();
        //when
        boolean actual = filmValidator.isTitleValid(title);
        //then
        Assertions.assertTrue(actual);
    }

    private static Stream<String> provideInvalidTitles() {
        return Stream.of(
                "",
                "    ",
                null,
                "a",
                "ga4dY2D2HVSXwiq4c46FsbTYOCLQR4qn3iaDmG8Vk6LVrm0sT3WdVbSe0KfvhF4ZBr7drt5QgoR5UO50jCGN2PYZrWOO0Gh5PHeamr" +
                        "2K8Hm90C6xQ6WOD9fM9iDDZ92TKSDHis",
                "<script> alert(123) <script>"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with title = {0}")
    @MethodSource("provideInvalidTitles")
    public void testIsTitleValidShouldReturnFalseWhenIsIncorrect(String title) {
        //given
        FilmValidator filmValidator = new FilmValidator();
        //when
        boolean actual = filmValidator.isTitleValid(title);
        //then
        Assertions.assertFalse(actual);
    }

    private static Stream<String> provideValidDirectors() {
        return Stream.of(
                "Mathias Arras",
                "Mathias d'Arras",
                "Martin Luther King, Jr.",
                "Hector Saulage-Hausen",
                "Андрей Тарковский"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with director = {0}")
    @MethodSource("provideValidDirectors")
    public void testIsDirectorValidShouldReturnTrueWhenIsCorrect(String director) {
        //given
        FilmValidator filmValidator = new FilmValidator();
        //when
        boolean actual = filmValidator.isDirectorValid(director);
        //then
        Assertions.assertTrue(actual);
    }

    private static Stream<String> provideInvalidDirectors() {
        return Stream.of(
                "",
                "    ",
                null,
                "233434",
                "@#$%",
                "a",
                "ga4dY2D2HVSXwiq4c46FsbTYOCLQR4qn3iaDmG8Vk6LVrm0sT3WdVbSe0KfvhF4ZBr7drt5QgoR5UO50jCGN2PYZrWOO0Gh5PHeamr" +
                        "2K8Hm90C6xQ6WOD9fM9iDDZ92TKSDHis",
                "<script> alert(123) <script>"
        );
    }

    @ParameterizedTest(name = "#{index} - Run test with director = {0}")
    @MethodSource("provideInvalidDirectors")
    public void testIsDirectorValidShouldReturnFalseWhenIsIncorrect(String director) {
        //given
        FilmValidator filmValidator = new FilmValidator();
        //when
        boolean actual = filmValidator.isDirectorValid(director);
        //then
        Assertions.assertFalse(actual);
    }

    @Test
    public void testIsYearValidShouldReturnTrueWhenIsCorrect() {
        //given
        FilmValidator filmValidator = new FilmValidator();
        //when
        boolean actual = filmValidator.isYearValid(CORRECT_YEAR);
        //then
        Assertions.assertTrue(actual);
    }

    @Test
    public void testIsYearValidShouldReturnFalseWhenIsIncorrect() {
        //given
        FilmValidator filmValidator = new FilmValidator();
        //when
        boolean actual = filmValidator.isYearValid(INCORRECT_YEAR);
        //then
        Assertions.assertFalse(actual);
    }
}
