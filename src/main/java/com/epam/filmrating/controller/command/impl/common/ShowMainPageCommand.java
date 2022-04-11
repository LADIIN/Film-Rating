package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * class ShowMainPageCommand
 *
 * @author Vladislav Darkovich
 */
public class ShowMainPageCommand implements Command {
    private static final String CURRENT_PAGE = "current_page";
    private static final String FILMS = "films";
    private static final String FILM_TYPE = "film_type";
    private static final String CURRENT_PAGE_NUMBER = "page";
    private static final String ELEMENTS = "elements";
    private static final String FILMS_ON_PAGE_ATTRIBUTE = "filmsOnPage";
    private static final String PAGE_PARAMETER = "&page=";
    private static final int FILMS_ON_PAGE = 10;
    private final FilmService filmService;

    public ShowMainPageCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        int currentPage = 1;
        String page = request.getParameter(CURRENT_PAGE_NUMBER);
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }
        String filmType = request.getParameter(FILM_TYPE);
        if (filmType.equals(FilmType.MOVIE.toString())) {
            List<Film> movies = filmService.findByTypeForPage(FilmType.MOVIE, currentPage, FILMS_ON_PAGE);
            request.setAttribute(FILMS, movies);
        } else {
            List<Film> series = filmService.findByTypeForPage(FilmType.SERIES, currentPage, FILMS_ON_PAGE);
            request.setAttribute(FILMS, series);
        }
        int filmsAmount = filmService.countFilmsOfType(FilmType.fromString(filmType));
        session.setAttribute(ELEMENTS, filmsAmount);
        session.setAttribute(FILM_TYPE, filmType);
        session.setAttribute(CURRENT_PAGE_NUMBER, currentPage);
        session.setAttribute(FILMS_ON_PAGE_ATTRIBUTE, FILMS_ON_PAGE);
        session.setAttribute(CURRENT_PAGE, Pages.MAIN_PAGE_REDIRECT + filmType + PAGE_PARAMETER + currentPage);
        return CommandResult.forward(Pages.MAIN_PAGE);
    }
}
