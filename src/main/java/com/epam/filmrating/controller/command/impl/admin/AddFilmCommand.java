package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Country;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.InputStream;
import java.time.Year;

/**
 * class AddFilmCommand
 *
 * @author Vladislav Darkovich
 */
public class AddFilmCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";
    private static final String IMAGE_INPUT_STREAM = "image_input_stream";
    private static final String IMAGE_NAME = "image_name";
    private static final String ERROR = "error";
    private static final String INCORRECT_DATA_ERROR = "film.incorrect.data";
    private static final String MESSAGE = "message";
    private static final String FILM_ADDED = "film.added";

    private final FilmService filmService;

    public AddFilmCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String title = request.getParameter(Film.TITLE);
        FilmType type = FilmType.fromString(request.getParameter(Film.TYPE));
        Genre genre = new Genre(request.getParameter(Film.GENRE));
        int yearInteger = Integer.parseInt(request.getParameter(Film.YEAR));
        Year year = Year.of(yearInteger);
        String director = request.getParameter(Film.DIRECTOR);
        Country country = new Country(request.getParameter(Film.COUNTRY));
        InputStream inputStream = (InputStream) request.getAttribute(IMAGE_INPUT_STREAM);
        String fileName = (String) request.getAttribute(IMAGE_NAME);
        boolean isAdded = filmService.addFilm(title, type, genre, year, director, country, fileName, inputStream);

        CommandResult commandResult;
        int currentPage = 1;
        if (isAdded) {
            session.setAttribute(MESSAGE, FILM_ADDED);
            commandResult = CommandResult.redirect(Pages.FILMS_PAGE_REDIRECT + currentPage);
        } else {
            request.setAttribute(ERROR, INCORRECT_DATA_ERROR);

            session.setAttribute(CURRENT_PAGE, Pages.ADD_FILM_PAGE_REDIRECT);
            commandResult = CommandResult.forward(Pages.ADD_FILM_PAGE);
        }
        return commandResult;
    }
}
