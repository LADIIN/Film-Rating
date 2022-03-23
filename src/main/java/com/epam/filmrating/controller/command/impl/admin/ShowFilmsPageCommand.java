package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ShowFilmsPageCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";
    private static final int FILMS_ON_PAGE = 5;
    private static final String FILMS = "films";
    private static final String CURRENT_PAGE_NUMBER = "page";
    private static final String PAGES = "pages";
    private static final String FILMS_ON_PAGE_ATTRIBUTE = "filmsOnPage";
    private static final String IS_FILM_ADDED = "isFilmAdded";
    private static final String MESSAGE = "message";
    private static final String FILM_ADDED = "Film was successfully added";

    private final FilmService filmService;

    public ShowFilmsPageCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        int currentPage = 1;
        String newPage = request.getParameter(CURRENT_PAGE_NUMBER);
        if (newPage != null) {
            currentPage = Integer.parseInt(newPage);
        }
        List<Film> films = filmService.findGroupForPage(currentPage, FILMS_ON_PAGE);
        int filmsAmount = filmService.countFilms();
        int pages = (int) Math.ceil(filmsAmount * 1.0 / FILMS_ON_PAGE);
        request.setAttribute(FILMS, films);
        session.setAttribute(CURRENT_PAGE_NUMBER, currentPage);
        session.setAttribute(PAGES, pages);
        session.setAttribute(FILMS_ON_PAGE_ATTRIBUTE, FILMS_ON_PAGE);

        if (session.getAttribute(IS_FILM_ADDED) != null) {
            request.setAttribute(MESSAGE, FILM_ADDED);
            session.removeAttribute(IS_FILM_ADDED);
        }
        session.setAttribute(CURRENT_PAGE, Pages.FILMS_PAGE_REDIRECT + currentPage);
        return CommandResult.forward(Pages.FILMS_PAGE);
    }
}
