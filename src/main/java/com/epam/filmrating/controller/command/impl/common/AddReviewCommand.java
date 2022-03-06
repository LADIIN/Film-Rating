package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.FilmService;
import com.epam.filmrating.model.service.ReviewService;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddReviewCommand implements Command {
    private static final String RATE = "rate";
    private static final String REVIEW_CONTENT = "reviewContent";
    private static final String FILM_ID = "filmId";
    private static final String USER = "user";

    private final ReviewService reviewService;
    private final FilmService filmService;
    private final UserService userService;

    public AddReviewCommand(ReviewService reviewService, FilmService filmService, UserService userService) {
        this.reviewService = reviewService;
        this.filmService = filmService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);

        int rate = Integer.parseInt(request.getParameter(RATE));
        String content = request.getParameter(REVIEW_CONTENT);
        Long filmId = Long.valueOf(request.getParameter(FILM_ID));
        Long userId = user.getId();

        boolean isAdded = reviewService.add(rate, content, filmId, userId);
        int reviewsOnFilm = reviewService.countFilmReviews(filmId);
        if (reviewsOnFilm % 5 == 0) {
            filmService.updateRating(filmId);
            userService.updateUsersStatusByReviewRate(filmId);
        }

        return CommandResult.redirect(Pages.FILM_PAGE_REDIRECT + filmId);
    }
}
