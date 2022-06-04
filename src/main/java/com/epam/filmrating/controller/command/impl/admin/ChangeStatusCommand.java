package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * class ChangeStatusCommand
 *
 * @author Vladislav Darkovich
 */
public class ChangeStatusCommand implements Command {
    private static final String STATUS = "status";
    private static final String ID = "id";
    private static final String CURRENT_PAGE_NUMBER = "page";

    private final UserService userService;

    public ChangeStatusCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(request.getParameter(ID));
        int status = Integer.parseInt(request.getParameter(STATUS));
        userService.changeStatus(id, status);
        int currentPage = (int) session.getAttribute(CURRENT_PAGE_NUMBER);
        return CommandResult.redirect(Pages.USERS_PAGE_REDIRECT + currentPage);
    }
}
