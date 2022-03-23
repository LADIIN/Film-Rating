package com.epam.filmrating.controller.command.impl.user;

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


public class ShowMainPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    public static final String CURRENT_PAGE = "current_page";
    public static final String FILMS = "films";
    public static final String FILM_TYPE = "film_type";
    private static final String CURRENT_PAGE_NUMBER = "page";
    private static final String PAGES = "pages";
    private static final String FILMS_ON_PAGE_ATTRIBUTE = "filmsOnPage";
    private static final int FILMS_ON_PAGE = 10;
    private static final String PAGE_PARAMETER = "&page=";
    private final FilmService filmService;

    public ShowMainPageCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String filmType = request.getParameter(FILM_TYPE);
        session.setAttribute(FILM_TYPE, filmType);
        int currentPage = 1;
        String newPage = request.getParameter(CURRENT_PAGE_NUMBER);
        if (newPage != null) {
            currentPage = Integer.parseInt(newPage);
        }

        int filmsAmount = filmService.countFilmsOfType(FilmType.fromString(filmType));
        int pages = (int) Math.ceil(filmsAmount * 1.0 / FILMS_ON_PAGE);
        session.setAttribute(CURRENT_PAGE_NUMBER, currentPage);
        session.setAttribute(PAGES, pages);
        session.setAttribute(FILMS_ON_PAGE_ATTRIBUTE, FILMS_ON_PAGE);

        if (filmType.equals(FilmType.MOVIE.toString())) {
            List<Film> movies = filmService.findByTypeForPage(FilmType.MOVIE, currentPage, FILMS_ON_PAGE);
            request.setAttribute(FILMS, movies);
        } else {
            List<Film> series = filmService.findByTypeForPage(FilmType.SERIES, currentPage, FILMS_ON_PAGE);
            request.setAttribute(FILMS, series);
        }

        session.setAttribute(CURRENT_PAGE, Pages.MAIN_PAGE_REDIRECT + filmType + PAGE_PARAMETER + currentPage);
        return CommandResult.forward(Pages.MAIN_PAGE);
    }
}
