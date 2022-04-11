package com.epam.filmrating.controller.command.impl.common;

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
 * class ShowRatingPageCommand
 *
 * @author Vladislav Darkovich
 */
public class ShowRatingPageCommand implements Command {
    public static final String FILMS = "films";
    public static final String CURRENT_PAGE = "current_page";
    public static final int RATING_SIZE = 10;

    private final FilmService filmService;

    public ShowRatingPageCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        List<Film> films = filmService.createRating(RATING_SIZE);
        session.setAttribute(FILMS, films);
        session.setAttribute(CURRENT_PAGE, Pages.RATING_PAGE_REDIRECT);
        return CommandResult.forward(Pages.RATING_PAGE);
    }
}
