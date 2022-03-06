package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.controller.command.SessionAttribute;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class ShowEditFilmPageCommand implements Command {
    private final FilmService filmService;
    private static final String ID = "id";
    private static final String FILM = "film";

    public ShowEditFilmPageCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(request.getParameter(ID));
        Optional<Film> filmOptional = filmService.findById(id);
        Film film = filmOptional.orElseThrow(IllegalArgumentException::new);
        session.setAttribute(FILM, film);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, Pages.EDIT_FILM_PAGE_REDIRECT + id);
        return CommandResult.forward(Pages.EDIT_FILM_PAGE);
    }
}
