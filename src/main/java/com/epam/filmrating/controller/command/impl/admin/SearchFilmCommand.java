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

/**
 * class SearchFilmCommand
 *
 * @author Vladislav Darkovich
 */
public class SearchFilmCommand implements Command {
    private static final String SEARCH_QUERY = "query";
    private static final String FILMS = "films";
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "search.incorrect.data";
    private static final String CURRENT_PAGE = "current_page";

    private final FilmService filmService;

    public SearchFilmCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String searchQuery = request.getParameter(SEARCH_QUERY);
        session.setAttribute(SEARCH_QUERY, searchQuery);


        if (searchQuery == null || searchQuery.length() < 3) {
            System.out.println("error");
            request.setAttribute(ERROR, ERROR_MESSAGE);
        } else {
            List<Film> films = filmService.searchByTitle(searchQuery);
            request.setAttribute(FILMS, films);
        }

        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        CommandResult commandResult;
        if (currentPage.contains(Pages.FILMS_PAGE_REDIRECT)) {
            commandResult = CommandResult.forward(Pages.SEARCH_FILM_RESULT_ADMIN);
        } else {
            commandResult = CommandResult.forward(Pages.SEARCH_RESULT_MAIN_PAGE);
        }


        return commandResult;
    }
}
