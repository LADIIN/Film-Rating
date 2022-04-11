package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.*;
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

/**
 * class ShowFilmPageCommand
 *
 * @author Vladislav Darkovich
 */
public class ShowFilmPageCommand implements Command {
    public static final String ID = "id";
    public static final String CURRENT_PAGE = "current_page";
    private static final String FILM = "film";
    private static final String REVIEWS = "reviews";
    private static final String IS_REVIEW_DELETED = "isReviewDeleted";
    private static final String MESSAGE = "message";
    private static final String REVIEW_DELETED = "review.deleted";

    private final FilmService filmService;
    private final ReviewService reviewService;

    public ShowFilmPageCommand(FilmService filmService, ReviewService reviewService) {
        this.filmService = filmService;
        this.reviewService = reviewService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.removeAttribute(MESSAGE);
        Long id = Long.valueOf(request.getParameter(ID));
        Optional<Film> filmOptional = filmService.findById(id);
        Film film = filmOptional.orElseThrow(IllegalArgumentException::new);
        session.setAttribute(FILM, film);
        List<Review> reviews = reviewService.findAllByFilmId(film.getId());

        session.getAttribute(IS_REVIEW_DELETED);

        Object isDeletedObject = session.getAttribute(IS_REVIEW_DELETED);
        if (isDeletedObject != null) {
            boolean isDeleted = (boolean) isDeletedObject;
            if (isDeleted) {
                session.setAttribute(MESSAGE, REVIEW_DELETED);
                session.removeAttribute(IS_REVIEW_DELETED);
            }
        }
        session.setAttribute(REVIEWS, reviews);
        session.setAttribute(CURRENT_PAGE, Pages.FILM_PAGE_REDIRECT + id);
        return CommandResult.forward(Pages.FILM_PAGE);
    }
}
