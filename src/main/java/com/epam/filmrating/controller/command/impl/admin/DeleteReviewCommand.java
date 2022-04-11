package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * class DeleteReviewCommand
 *
 * @author Vladislav Darkovich
 */
public class DeleteReviewCommand implements Command {
    private static final String ID = "id";
    public static final String CURRENT_PAGE = "current_page";
    private static final String IS_REVIEW_DELETED = "isReviewDeleted";
    private final ReviewService reviewService;

    public DeleteReviewCommand(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long reviewId = Long.valueOf(request.getParameter(ID));
        boolean isDeleted = reviewService.deleteReview(reviewId);

        session.setAttribute(IS_REVIEW_DELETED, isDeleted);
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        return CommandResult.redirect(currentPage);
    }
}
