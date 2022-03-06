package com.epam.filmrating.controller.command.impl.user;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.CommandException;
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
    private final FilmService filmService;

    public ShowMainPageCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        try {
            List<Film> movies = filmService.findByType(FilmType.MOVIE);
            request.setAttribute(RequestAttribute.MOVIES, movies);
            List<Film> series = filmService.findByType(FilmType.SERIES);
            request.setAttribute(RequestAttribute.SERIES, series);
        } catch (ServiceException e) {
            LOGGER.error("Loading films error caused by {}", e.getMessage());
            throw new CommandException("Loading films error caused by ", e);
        }
        session.setAttribute(SessionAttribute.CURRENT_PAGE, Pages.MAIN_PAGE_REDIRECT);
        return CommandResult.forward(Pages.MAIN_PAGE);
    }
}
