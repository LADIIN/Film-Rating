package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    private final UserService userService;
    private static final String LOGIN_ERROR = "login.error";
    private static final String USER_BLOCKED_MESSAGE = "user.blocked.message";
    private static final String IS_ADMIN = "isAdmin";

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Optional<User> userOptional = userService.login(login, password);
        CommandResult result;

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.isBlocked()){
                request.setAttribute(RequestAttribute.ERROR, USER_BLOCKED_MESSAGE);
                result = CommandResult.forward(Pages.LOGIN_PAGE);
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute(RequestAttribute.USER, user);
                result = CommandResult.redirect(Pages.MAIN_PAGE_REDIRECT);
            }
        } else {
            request.setAttribute(RequestAttribute.ERROR, LOGIN_ERROR);
            result = CommandResult.forward(Pages.LOGIN_PAGE);
        }
        return result;
    }
}
