package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.Review;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.ReviewService;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.epam.filmrating.model.entity.Review.FILM_ID;

/**
 * class AddReviewCommand
 *
 * @author Vladislav Darkovich
 */
public class AddReviewCommand implements Command {
    private static final String USER = "user";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String ERROR = "error";
    private static final String INCORRECT_DATA_ERROR = "film.incorrect.data";
    private static final int REVIEWS_NEEDED_FOR_UPDATE = 5;

    private final ReviewService reviewService;
    private final FilmService filmService;
    private final UserService userService;

    public AddReviewCommand(ReviewService reviewService, FilmService filmService, UserService userService) {
        this.reviewService = reviewService;
        this.filmService = filmService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        int rate = Integer.parseInt(request.getParameter(Review.RATE));
        String content = request.getParameter(Review.CONTENT);
        Long filmId = Long.valueOf(request.getParameter(FILM_ID));
        Long userId = user.getId();
        boolean isAdded = reviewService.add(rate, content, filmId, userId);

        CommandResult commandResult;
        if (isAdded) {
            int reviewsOnFilm = reviewService.countFilmReviews(filmId);
            if (reviewsOnFilm % REVIEWS_NEEDED_FOR_UPDATE == 0) {
                filmService.updateRating(filmId);
                userService.updateUsersStatusByReviewRate(filmId);
            }
            commandResult = CommandResult.redirect(Pages.FILM_PAGE_REDIRECT + filmId);
        } else {
            request.setAttribute(ERROR, INCORRECT_DATA_ERROR);
            session.setAttribute(CURRENT_PAGE, Pages.FILMS_PAGE_REDIRECT + filmId);

            commandResult = CommandResult.forward(Pages.FILM_PAGE);
        }
        return commandResult;
    }
}
