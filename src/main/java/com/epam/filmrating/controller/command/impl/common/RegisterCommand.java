package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

/**
 * class RegisterCommand
 *
 * @author Vladislav Darkovich
 */
public class RegisterCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String CURRENT_PAGE = "current_page";
    private static final String USER = "user";
    private static final String ERROR = "registerError";
    private static final String LOGGING_ERROR = "error.logging";
    private static final String INCORRECT_DATA = "register.incorrect.data";
    private static final String PAGE_PARAMETER = "&page=";
    private static final String USER_ALREADY_EXIST = "register.user.exist";
    private static final int PAGE_NUMBER = 1;

    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);

        CommandResult commandResult;

        if (!userService.isUserAlreadyExist(login, email)) {
            boolean isRegistered = userService.register(login, password, email);
            if (isRegistered) {
                Optional<User> userOptional = userService.login(login, password);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    session.setAttribute(USER, user);
                    commandResult = CommandResult.redirect(Pages.MAIN_PAGE_REDIRECT + FilmType.MOVIE + PAGE_PARAMETER + PAGE_NUMBER);
                } else {
                    session.setAttribute(ERROR, LOGGING_ERROR);
                    commandResult = CommandResult.forward(Pages.REGISTER_PAGE);
                }
            } else {
                session.setAttribute(ERROR, INCORRECT_DATA);
                commandResult = CommandResult.forward(Pages.REGISTER_PAGE);
            }
        } else {
            session.setAttribute(ERROR, USER_ALREADY_EXIST);
            commandResult = CommandResult.forward(Pages.REGISTER_PAGE);
        }
        session.setAttribute(CURRENT_PAGE, Pages.REGISTER_PAGE_REDIRECT);
        return commandResult;
    }
}
