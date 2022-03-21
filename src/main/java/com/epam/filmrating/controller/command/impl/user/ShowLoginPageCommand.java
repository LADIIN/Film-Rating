package com.epam.filmrating.controller.command.impl.user;

import com.epam.filmrating.controller.command.Command;
import com.epam.filmrating.controller.command.CommandResult;
import com.epam.filmrating.controller.command.Pages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowLoginPageCommand implements Command {
    public static final String CURRENT_PAGE = "current_page";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, Pages.LOGIN_PAGE);
        return CommandResult.redirect(Pages.LOGIN_PAGE);
    }
}
