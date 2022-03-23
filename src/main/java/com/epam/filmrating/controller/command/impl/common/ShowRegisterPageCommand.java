package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowRegisterPageCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, Pages.REGISTER_PAGE_REDIRECT);
        return CommandResult.forward(Pages.REGISTER_PAGE);
    }
}
