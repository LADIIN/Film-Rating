package com.epam.filmrating.controller.command.impl.common;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import com.epam.filmrating.exception.CommandException;
import com.epam.filmrating.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, CommandException {
        request.getSession().invalidate();
        return CommandResult.redirect(Pages.LOGIN_PAGE_REDIRECT);
    }
}
