package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    private static final String LOGIN_ERROR = "login.error";
    private static final String USER_BLOCKED_MESSAGE = "user.blocked.message";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String USER = "user";
    public static final String ERROR = "errorMessage";
    private static final String PAGE_PARAMETER = "&page=";
    private static final int PAGE_NUMBER = 1;
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        Optional<User> userOptional = userService.login(login, password);
        CommandResult result;

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isBlocked()) {
                request.setAttribute(ERROR, USER_BLOCKED_MESSAGE);
                result = CommandResult.forward(Pages.LOGIN_PAGE);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(USER, user);
                result = CommandResult.redirect(Pages.MAIN_PAGE_REDIRECT + FilmType.MOVIE + PAGE_PARAMETER + PAGE_NUMBER);
            }
        } else {
            request.setAttribute(ERROR, LOGIN_ERROR);
            result = CommandResult.forward(Pages.LOGIN_PAGE);
        }
        return result;
    }
}
