package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class DeleteFilmCommand implements Command {
    public static final String ID = "id";
    private static final String CURRENT_PAGE_NUMBER = "page";
    private final FilmService filmService;

    public DeleteFilmCommand(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(request.getParameter(ID));
        boolean isDeleted = filmService.deleteFilm(id);
        int currentPage = (int) session.getAttribute(CURRENT_PAGE_NUMBER);

        return CommandResult.redirect(Pages.FILMS_PAGE_REDIRECT + currentPage);
    }
}
