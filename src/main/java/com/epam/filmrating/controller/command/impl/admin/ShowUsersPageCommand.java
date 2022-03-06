package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.controller.command.SessionAttribute;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ShowUsersPageCommand implements Command {
    private final UserService userService;

    private static final String USERS = "users";
    private static final String CURRENT_PAGE_NUMBER = "page";
    private static final String PAGES = "pages";
    private static final int USERS_ON_PAGE = 5;
    private static final String USERS_ON_PAGE_ATTRIBUTE = "usersOnPage";

    public ShowUsersPageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException {
        HttpSession session = request.getSession();

        int currentPage = 1;
        String newPage = request.getParameter(CURRENT_PAGE_NUMBER);
        if (newPage != null) {
            currentPage = Integer.parseInt(newPage);
        }
        List<User> users = userService.findGroupForPage(currentPage, USERS_ON_PAGE);
        int usersAmount = userService.countUsers();
        int pages = (int) Math.ceil(usersAmount * 1.0 / USERS_ON_PAGE);

        session.setAttribute(CURRENT_PAGE_NUMBER, currentPage);
        session.setAttribute(PAGES, pages);
        session.setAttribute(USERS_ON_PAGE_ATTRIBUTE, USERS_ON_PAGE);
        request.setAttribute(USERS, users);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, Pages.USERS_PAGE_REDIRECT + currentPage);
        return CommandResult.forward(Pages.USERS_PAGE);
    }
}
