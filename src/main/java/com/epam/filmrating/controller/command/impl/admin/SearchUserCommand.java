package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * class SearchUserCommand
 *
 * @author Vladislav Darkovich
 */
public class SearchUserCommand implements Command {
    private static final String SEARCH_QUERY = "query";
    private static final String USERS = "users";
    private static final String ERROR = "error";
    private static final String ERROR_MESSAGE = "search.incorrect.data";

    private final UserService userService;

    public SearchUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String searchQuery = request.getParameter(SEARCH_QUERY);
        session.setAttribute(SEARCH_QUERY, searchQuery);

        if (searchQuery == null || searchQuery.length() < 3) {
            request.setAttribute(ERROR, ERROR_MESSAGE);
        } else {
            List<User> users = userService.searchUsersByLogin(searchQuery);
            request.setAttribute(USERS, users);
        }
        return CommandResult.forward(Pages.SEARCH_USERS_RESULT_PAGE);
    }
}

