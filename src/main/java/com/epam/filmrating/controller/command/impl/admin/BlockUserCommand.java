package com.epam.filmrating.controller.command.impl.admin;

import com.epam.filmrating.controller.command.*;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * class BlockUserCommand
 *
 * @author Vladislav Darkovich
 */
public class BlockUserCommand implements Command {
    private static final String CURRENT_PAGE_NUMBER = "page";
    public static final String ID = "id";

    private final UserService userService;

    public BlockUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long id = Long.valueOf(request.getParameter(ID));
        boolean isUpdated = userService.setBlockStatus(id);
        int currentPage = (int) session.getAttribute(CURRENT_PAGE_NUMBER);

        return CommandResult.redirect(Pages.USERS_PAGE_REDIRECT + currentPage);
    }
}
