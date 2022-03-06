package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Country;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.model.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.Year;

public class EditFilmCommand implements Command {
    private static final String ID = "id";
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String GENRE = "genre";
    public static final String YEAR = "year";
    public static final String DIRECTOR = "director";
    public static final String COUNTRY = "country";
    public static final String RATING = "rating";
    public static final String POSTER_NAME = "poster_path";
    private final FilmService filmService;

    public EditFilmCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(request.getParameter(ID));
        String title = request.getParameter(TITLE);
        FilmType type = FilmType.fromString(request.getParameter(TYPE));
        Genre genre = Genre.fromString(request.getParameter(GENRE));
        int yearInteger = Integer.parseInt(request.getParameter(YEAR));
        Year year = Year.of(yearInteger);
        String director = request.getParameter(DIRECTOR);
        Country country = Country.fromString(request.getParameter(COUNTRY));
        String posterName = request.getParameter(POSTER_NAME);

        filmService.editFilm(id, title, type, genre, year, director, country, posterName);
        int currentPage = 1;
        return CommandResult.redirect(Pages.FILMS_PAGE_REDIRECT + currentPage);
    }
}
