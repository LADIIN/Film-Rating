package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class LoginCommand implements Command {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Optional<User> user = userService.login(login, password);
        CommandResult result;

        if (user.isPresent()) {
            request.getSession().setAttribute(RequestAttribute.USER, user.get());
            result = CommandResult.redirect(Pages.MAIN_PAGE_REDIRECT);
        } else {
            request.setAttribute(RequestAttribute.ERROR, LocaleMessageKey.LOGIN_ERROR);
            result = CommandResult.forward(Pages.LOGIN_PAGE);
        }
        return result;
    }
}
