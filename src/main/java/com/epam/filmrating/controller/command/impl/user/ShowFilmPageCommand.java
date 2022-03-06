package com.epam.filmrating.controller.command.impl.user;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.Review;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;


public class ShowFilmPageCommand implements Command {
    private final FilmService filmService;
    private final ReviewService reviewService;

    private static final String FILM = "film";
    private static final String REVIEWS = "reviews";

    public ShowFilmPageCommand(FilmService filmService, ReviewService reviewService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(request.getParameter(RequestParameter.ID));
        Optional<Film> filmOptional = filmService.findById(id);
        Film film = filmOptional.orElseThrow(IllegalArgumentException::new);
        session.setAttribute(FILM, film);
        List<Review> reviews = reviewService.findAllByFilmId(film.getId());
        session.setAttribute(REVIEWS, reviews);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, Pages.FILM_PAGE_REDIRECT + id);
        return CommandResult.forward(Pages.FILM_PAGE);
    }
}
